package com.joker.solitaire


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_undo -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_theme -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_splash)

        GamePresenter.setGameView(this)
        GameModel.resetGame()

        verticalLayout {
            leftPadding = dip(4)
            rightPadding = dip(4)
            topPadding = dip(8)
            linearLayout{
                button(""){
                    textSize = 26f

                }.lparams(width= wrapContent,height = dip(80)){
                    horizontalMargin=dip(5)
                    verticalMargin=dip(10)
                }
            }
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
            }.lparams(height = matchParent) {
                topMargin = cardHeight / 2
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
