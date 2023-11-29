package com.example.myfavmovies.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.myfavmovies.R
import com.example.myfavmovies.model.Movies
import com.example.myfavmovies.ui.theme.MyFavMoviesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeDataMovies = Movies(
        id = 0,
        name = "From London To Bali",
        year = 2017,
        image = R.drawable.movie_fromlondontobali,
        description =
        "From London to Bali adalah film drama romantis Indonesia yang dirilis pada tahun 2017. Film ini disutradarai oleh Angling Sagaran dan dibintangi oleh Ricky Harun, Jessica Mila, Nikita Willy.\n" +
                "\n" +
                "Film ini bercerita tentang Lukman (Ricky Harun), seorang pemuda asal Jakarta yang putus cinta dengan kekasihnya, Dewi (Jessica Mila). Lukman memutuskan untuk pergi ke London untuk melupakan Dewi. Di London, Lukman bertemu dengan Putu (Nikita Willy), seorang gadis Bali yang bekerja sebagai tour guide.\n" +
                "\n" +
                "Lukman dan Putu mulai dekat dan saling jatuh cinta. Namun, hubungan mereka tidak berjalan mulus. Dewi yang mengetahui Lukman ada di London, menyusul Lukman ke London. Dewi berusaha untuk kembali ke pelukan Lukman.\n" +
                "\n" +
                "Di sisi lain, Putu juga harus menghadapi kenyataan bahwa Lukman adalah seorang pria yang telah memiliki kekasih. Putu akhirnya memutuskan untuk mundur dari hubungannya dengan Lukman.\n" +
                "\n" +
                "Lukman yang kecewa dengan keputusan Putu, akhirnya memutuskan untuk kembali ke Jakarta. Di Jakarta, Lukman bertemu dengan Dewi. Dewi yang telah menyadari kesalahannya, meminta maaf kepada Lukman. Lukman akhirnya memaafkan Dewi dan mereka kembali menjalin hubungan.\n" +
                "\n" +
                "Film From London to Bali adalah film yang ringan dan menghibur. Film ini cocok untuk ditonton oleh para pecinta film drama romantis. Film ini juga mengajarkan kita tentang arti cinta dan pengorbanan.\n" +
                "\n" +
                "Berikut adalah beberapa hal yang menarik dari film From London to Bali:\n" +
                "\n" +
                "- Lokasi syuting yang indah. Film ini mengambil lokasi syuting di Jakarta, London, dan Bali. Pemandangan-pemandangan yang indah di ketiga kota tersebut, menambah keindahan film ini.\n" +
                "- Chemistry yang kuat antara para pemain. Ricky Harun dan Jessica Mila, serta Ricky Harun dan Nikita Willy, memiliki chemistry yang kuat. Hal ini membuat hubungan mereka di film terlihat realistis dan menyentuh.\n" +
                "- Pesan moral yang sederhana namun kuat. Film ini mengajarkan kita tentang arti cinta dan pengorbanan. Cinta sejati adalah cinta yang dapat menerima kekurangan pasangannya.",
        actor = "Ricky Harun • Nikita Willy • Jessica Mila",
        rating = 6.2,
        isFavorite = false
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyFavMoviesTheme {
                DetailInformation(
                    id = fakeDataMovies.id,
                    name = fakeDataMovies.name,
                    year = fakeDataMovies.year,
                    image = fakeDataMovies.image,
                    description = fakeDataMovies.description,
                    actor = fakeDataMovies.actor,
                    rating = fakeDataMovies.rating,
                    isFavorite = fakeDataMovies.isFavorite,
                    navigateBack = {},
                    onFavoriteButtonClicked = {_, _ ->}
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(fakeDataMovies.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataMovies.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        // Assert that the favorite button is displayed
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        // Assert that the content description of the favorite button is correct based on the isFavorite state
        val isFavorite = fakeDataMovies.isFavorite // Set the isFavorite state here
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}