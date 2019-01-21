package com.joker.solitaire

import android.content.Context
import android.graphics.Color
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewManager
import android.widget.ImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView


class TableauPileView(context: Context, val index: Int) : _RelativeLayout(context) {


    init {
        addViews()
    }

    var listener = View.OnDragListener { view, dragEvent ->

        if(dragEvent.action == DragEvent.ACTION_DRAG_STARTED){
            // As an example of what your application might do,
            // applies a blue color tint to the View to indicate that it can accept
            // data.
            (view as? ImageView)?.setColorFilter(Color.BLUE)

            // Invalidate the view to force a redraw in the new tint
            view.invalidate()
        }

        if(dragEvent.action == DragEvent.ACTION_DRAG_ENDED){

        }
        true
    }


    fun update() {
        removeAllViews()
        addViews()
    }

    private fun addViews() {
        val cards = GameModel.tableauPiles[index].cards
        cards.forEachIndexed { i, card ->
            imageView {
                y = (i * dip(25)).toFloat()
                imageResource = if (card.faceUp) getResIdForCard(card) else cardBackDrawable
                onClick {
                    GamePresenter.onTableauTap(index, i)
                }
                if (card.faceUp) setOnDragListener(listener)
            }.lparams(context.cardWidth, context.cardHeight)
        }
    }
}

fun ViewManager.tableauPileView(index: Int, init: TableauPileView.() -> Unit = {}) =
        ankoView({ TableauPileView(it, index) }, 0, init)