package com.example.myfavmovies

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.myfavmovies.model.MoviesData
import com.example.myfavmovies.navigation.Screen
import com.example.myfavmovies.ui.theme.MyFavMoviesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MyFavMoviesKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyFavMoviesTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MyFavMovies(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    // klik item pada lazy list, lalu cek apakah item yang dituju sesuai dengan yang diharapkan
    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(5)
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailMovies.route)
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[5].name).assertIsDisplayed()
    }

    // melakukan navigasi antar screen, lalu cek apakah screen yang dituju sesuai dengan yang diharapkan
    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    // melakukan navigasi ke halaman about, lalu cek apakah data yang ditampilkan sesuai dengan yang diharapkan
    @Test
    fun navigateTo_AboutPage() {
        composeTestRule.onNodeWithStringId(R.string.profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.profile_name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.profile_email).assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang salah, lalu cek apakah data yang dicari tidak ada di list
    @Test
    fun searchShowEmptyListMovies() {
        val incorrectSearch = "aa31z"
        composeTestRule.onNodeWithStringId(R.string.search_text).performTextInput(incorrectSearch)
        composeTestRule.onNodeWithTag("emptyList").assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang benar, lalu cek apakah data yang dicari ada di list
    @Test
    fun searchShowListMovies() {
        val rightSearch = "alex"
        composeTestRule.onNodeWithStringId(R.string.search_text).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("alex").assertIsDisplayed()
    }

    // Klik favorite di detail screen, lalu cek apakah data favorite tersedia di favorite screen
    @Test
    fun favoriteClickInDetailScreen_ShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailMovies.route)
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("back_home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[0].name).assertIsDisplayed()
    }

    // Klik favorite dan delete favorite di detail screen, lalu cek apakah data tidak ada di favorite screen
    @Test
    fun favoriteClickAndDeleteFavoriteInDetailScreen_NotShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailMovies.route)
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("back_home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.emptyfavorite).assertIsDisplayed()
    }

    // Klik favorite di home screen, lalu cek apakah data favorite tersedia di favorite screen
    @Test
    fun favoriteClickInHome_ShowInFavoriteScreen() {
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(0)
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[0].name).assertIsDisplayed()
    }

    // Klik favorite dan delete favorite di home screen, lalu cek apakah data tidak ada di favorite screen
    @Test
    fun favoriteClickAndDeleteFavoriteInHome_NotShowInFavoriteScreen() {
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(0)
        composeTestRule.onNodeWithText(MoviesData.dummyMovies[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.emptyfavorite).assertIsDisplayed()
    }

}