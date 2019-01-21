package com.joker.solitaire


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.*

val cardBackDrawable = R.drawable.jokerdeck
val emptyPileDrawable = R.drawable.jokerdecksdrawable
fun View.getResIdForCard(card: Card): Int {
    val resourceName = "card${card.suit}${cardsMap[card.value]}".toLowerCase()
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}
val Context.cardWidth: Int
                get() {
                    if(Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation)
                        return (displayMetrics.heightPixels - dip(8)) / 7

                    return (displayMetrics.widthPixels - dip(8)) / 7
                }


val Context.cardHeight: Int
    get() {
        if(Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation)
            return ((displayMetrics.widthPixels - dip(8)) / 10)

        return cardWidth * 190 / 140
    }


class MainActivity : AppCompatActivity(), GameView {
    var deckView: DeckView? = null
    var wastePileView: WastePileView? = null
    val foundationPileViews: Array<FoundationPileView?> = arrayOfNulls(4)
    val tableauPileViews: Array<TableauPileView?> = arrayOfNulls(7)
    private var mTextMessage: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_splash)

        GamePresenter.setGameView(this)
        GameModel.resetGame()



        verticalLayout {
            leftPadding = dip(4)
            rightPadding = dip(4)
            topPadding = dip(8)




            linearLayout {
                deckView = deckView().lparams(cardWidth, cardHeight)
                wastePileView = wastePileView().lparams(cardWidth, cardHeight)
                view().lparams(cardWidth, 0)
                for (i in 0..3) {
                    foundationPileViews[i] = foundationPileView(i).lparams(cardWidth, cardHeight)
                }
            }
            linearLayout {
                for (i in 0..6) {
                    tableauPileViews[i] = tableauPileView(i).lparams(cardWidth, matchParent)
                }
            }.lparams(height = dip(520)) {
                topMargin = cardHeight / 2
            }

            linearLayout{
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                imageButton{
                    imageResource = R.drawable.ic_sync_black_24dp
                    background = null
                    drawingCacheBackgroundColor = Color.WHITE
                    onClick {
                        val intent = getIntent() as Intent
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        finish()
                        overridePendingTransition(0,0)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                    }

                }.lparams(width= displayMetrics.widthPixels/4,height = dip(46))
                imageButton{
                    imageResource = R.drawable.ic_dashboard_white_24dp
                    background = null
                    onClick {
                        val intent = Intent(this@MainActivity,ThemeActivity::class.java)
                        startActivity(intent)
                    }
                }.lparams(width= displayMetrics.widthPixels/4,height = dip(46))
                imageButton{
                    imageResource = R.drawable.ic_build_black_24dp
                    background = null
                    onClick {
                        val intent = Intent(this@MainActivity,SettingsActivity::class.java)
                        startActivity(intent)
                    }
                }.lparams(width= displayMetrics.widthPixels/4,height = dip(46))
                imageButton{
                    imageResource = R.drawable.ic_refresh_black_24dp
                    background = null
                }.lparams(width= displayMetrics.widthPixels/4,height = dip(46))


            }
        }


    }

    override fun update() {
        deckView!!.update()
        wastePileView!!.update()
        foundationPileViews.forEach {
            it!!.update()
        }
        tableauPileViews.forEach {
            it!!.update()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Start Over")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        GameModel.resetGame()
        update()
        return true
    }



}
