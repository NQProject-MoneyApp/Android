package com.nqproject.MoneyApp.repository

import android.util.Log
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R


enum class MoneyAppIcon {
    Hamburger,
    BeerHamburger,
    Bowl,
    Drinks,
    Coffee,
    Beers,
    Kite;

    fun icon(): Int {
       return when (this) {
            Hamburger -> R.drawable.ic_burger
            BeerHamburger -> R.drawable.ic_beer_hamburger
            Bowl -> R.drawable.ic_bowl
            Drinks -> R.drawable.ic_drinks
            Coffee -> R.drawable.ic_coffe
            Beers -> R.drawable.ic_beers
            Kite -> R.drawable.ic_kite
        }
    }

    companion object {
        fun from(id: Int): MoneyAppIcon {
            return when (id) {
                1 -> Hamburger
                2 -> BeerHamburger
                3 -> Bowl
                4 -> Drinks
                5 -> Coffee
                6 -> Beers
                7 -> Kite
                else -> {
                    Log.d(Config.MAIN_TAG, "Error icon not found")
                    Hamburger
                }
            }
        }
    }
}
