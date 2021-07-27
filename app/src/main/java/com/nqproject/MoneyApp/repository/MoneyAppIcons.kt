package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.R

enum class MoneyAppIcon(val id: Int) {
    Hamburger(1),
    BeerHamburger(2),
    Bowl(3),
    Drinks(4),
    Coffee(5),
    Beers(6),
    Kite(7);


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
            return values().firstOrNull { it.id == id} ?: Hamburger
        }
    }
}
