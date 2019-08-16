package com.lambdaschool.sprint2_challenge

import com.lambdaschool.sprint2_challenge.ShoppingItemConstants.ICON_IDS
import com.lambdaschool.sprint2_challenge.ShoppingItemConstants.ITEM_NAMES_RAW

class ItemsRepo {
    companion object { //using companion object so I don't have to instantiate the class
        val itemsList = mutableListOf<Items>()
        fun createItemsList() {
            for(i in 0 until ITEM_NAMES_RAW.size) {
                itemsList.add(Items(ITEM_NAMES_RAW[i], ICON_IDS[i], false))
            }
        }
    }
}