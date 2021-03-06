package com.mho.training

import android.app.Application
import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.mho.training.di.DaggerUiTestComponent
import com.mho.training.features.main.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class UITests {

    @ExperimentalTime
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    private lateinit var mockWebServer: MockWebServer
    private lateinit var resource: OkHttp3IdlingResource

    @ExperimentalTime
    @Before
    fun setUp() {
        val instrumentation= InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as Application
        val component = DaggerUiTestComponent.factory().create(app)

        mockWebServer = component.mockWebServer

        resource = OkHttp3IdlingResource.create("OkHttp", component.movieRequest.okHttpClient)
        IdlingRegistry.getInstance().register(resource)

        val intent = Intent(instrumentation.targetContext, MainActivity::class.java)

        activityTestRule.launchActivity(intent)
    }

    @ExperimentalTime
    @Test
    fun clickAMovieNavigatesToItsDetail() {
        val result = "{\"results\":[{\"popularity\":243.267,\"vote_count\":330,\"video\":false,\"poster_path\":\"\\/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg\",\"id\":454626,\"adult\":false,\"backdrop_path\":\"\\/qonBhlm0UjuKX2sH7e73pnG0454.jpg\",\"original_language\":\"en\",\"original_title\":\"Sonic the Hedgehog\",\"genre_ids\":[28,35,878,10751],\"title\":\"Sonic the Hedgehog\",\"vote_average\":7.1,\"overview\":\"Based on the global blockbuster videogame franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination.\",\"release_date\":\"2020-02-14\"},{\"popularity\":153.984,\"vote_count\":755,\"video\":false,\"poster_path\":\"\\/h4VB6m0RwcicVEZvzftYZyKXs6K.jpg\",\"id\":495764,\"adult\":false,\"backdrop_path\":\"\\/uozb2VeD87YmhoUP1RrGWfzuCrr.jpg\",\"original_language\":\"en\",\"original_title\":\"Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn)\",\"genre_ids\":[28,35,80],\"title\":\"Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn)\",\"vote_average\":6.8,\"overview\":\"After her breakup with the Joker, Harley Quinn joins forces with singer Black Canary, assassin Huntress, and police detective Renee Montoya to help a young girl named Cassandra, who had a hit placed on her after she stole a rare diamond from crime lord Roman Sionis.\",\"release_date\":\"2020-02-07\"},{\"popularity\":109.459,\"vote_count\":27,\"video\":false,\"poster_path\":\"\\/33VdppGbeNxICrFUtW2WpGHvfYc.jpg\",\"id\":481848,\"adult\":false,\"backdrop_path\":\"\\/yFRpUmsreYO5Bc0HVBTsJsHIIox.jpg\",\"original_language\":\"en\",\"original_title\":\"The Call of the Wild\",\"genre_ids\":[12,18,10751],\"title\":\"The Call of the Wild\",\"vote_average\":5.9,\"overview\":\"A sled dog struggles for survival in the wilds of the Yukon.\",\"release_date\":\"2020-02-21\"},{\"popularity\":121.492,\"vote_count\":432,\"video\":false,\"poster_path\":\"\\/5eNiYMu2GXCtNlDwMcJqKGVwyoX.jpg\",\"id\":448119,\"adult\":false,\"backdrop_path\":\"\\/lG802rseTZcN9mtLsQPVfApEVzM.jpg\",\"original_language\":\"en\",\"original_title\":\"Dolittle\",\"genre_ids\":[12,35,14,10751],\"title\":\"Dolittle\",\"vote_average\":6.4,\"overview\":\"After losing his wife seven years earlier, the eccentric Dr. John Dolittle, famed doctor and veterinarian of Queen Victoria’s England, hermits himself away behind the high walls of Dolittle Manor with only his menagerie of exotic animals for company. But when the young queen falls gravely ill, a reluctant Dolittle is forced to set sail on an epic adventure to a mythical island in search of a cure, regaining his wit and courage as he crosses old adversaries and discovers wondrous creatures.\",\"release_date\":\"2020-01-17\"},{\"popularity\":72.954,\"vote_count\":4,\"video\":false,\"poster_path\":\"\\/ji1JO9bZX3pQ15lhU96dj0gZO74.jpg\",\"id\":526007,\"adult\":false,\"backdrop_path\":\"\\/49Qeu6wfw8lKgMs0vpOQEUZhSAg.jpg\",\"original_language\":\"en\",\"original_title\":\"The Night Clerk\",\"genre_ids\":[80,18],\"title\":\"The Night Clerk\",\"vote_average\":8,\"overview\":\"Hotel night clerk Bart Bromley is a highly intelligent young man on the Autism spectrum. When a woman is murdered during his shift, Bart becomes the prime suspect. As the police investigation closes in, Bart makes a personal connection with a beautiful guest named Andrea, but soon realises he must stop the real murderer before she becomes the next victim.\",\"release_date\":\"2020-02-21\"},{\"popularity\":72.789,\"vote_count\":25,\"video\":false,\"poster_path\":\"\\/qZ1KAgfdeNbzrNYKW4BIRHdEBJ9.jpg\",\"id\":666750,\"adult\":false,\"backdrop_path\":\"\\/6mKAKhj8POVGqV1GsroS5mGIUe9.jpg\",\"original_language\":\"en\",\"original_title\":\"Dragonheart: Vengeance\",\"genre_ids\":[14],\"title\":\"Dragonheart: Vengeance\",\"vote_average\":6.9,\"overview\":\"Lukas, a young farmer whose family is killed by savage raiders in the countryside, sets out on an epic quest for revenge, forming an unlikely trio with a majestic dragon and a swashbuckling, sword-fighting mercenary, Darius.\",\"release_date\":\"2020-02-04\"},{\"popularity\":106.796,\"vote_count\":889,\"video\":false,\"poster_path\":\"\\/y95lQLnuNKdPAzw9F9Ab8kJ80c3.jpg\",\"id\":38700,\"adult\":false,\"backdrop_path\":\"\\/upUy2QhMZEmtypPW3PdieKLAHxh.jpg\",\"original_language\":\"en\",\"original_title\":\"Bad Boys for Life\",\"genre_ids\":[28,80,53],\"title\":\"Bad Boys for Life\",\"vote_average\":6.4,\"overview\":\"Marcus and Mike are forced to confront new threats, career changes, and midlife crises as they join the newly created elite team AMMO of the Miami police department to take down the ruthless Armando Armas, the vicious leader of a Miami drug cartel.\",\"release_date\":\"2020-01-17\"},{\"popularity\":46.307,\"vote_count\":6,\"video\":false,\"poster_path\":\"\\/tIpGQ9uuII7QVFF0GHCFTJFfXve.jpg\",\"id\":555974,\"adult\":false,\"backdrop_path\":\"\\/tE7SjDwITs333u4fBICqEmCRfgk.jpg\",\"original_language\":\"en\",\"original_title\":\"Brahms: The Boy II\",\"genre_ids\":[27,9648,53],\"title\":\"Brahms: The Boy II\",\"vote_average\":7.4,\"overview\":\"After a family moves into the Heelshire Mansion, their young son soon makes friends with a life-like doll called Brahms.\",\"release_date\":\"2020-02-20\"},{\"popularity\":28.218,\"vote_count\":0,\"video\":false,\"poster_path\":\"\\/eLyvKX6F9qZWrCIGpRjCAAX9mWn.jpg\",\"id\":574489,\"adult\":false,\"backdrop_path\":\"\\/A5NyaAC4zohcbgQPyRhZJAcvwH1.jpg\",\"original_language\":\"en\",\"original_title\":\"Goldie\",\"genre_ids\":[18],\"title\":\"Goldie\",\"vote_average\":0,\"overview\":\"Goldie, a precocious teenager in a family shelter, wages war against the system to keep her sisters together while she pursues her dreams of being a dancer. This is a story about displaced youth, ambition, and maintaining your spirit in the face of insurmountable obstacles.\",\"release_date\":\"2020-02-21\"},{\"popularity\":43.661,\"vote_count\":17,\"video\":false,\"poster_path\":\"\\/sm8iVzA7kRp0d4BSIsgXjsSBMKV.jpg\",\"id\":556678,\"adult\":false,\"backdrop_path\":\"\\/l111ATIHwHQPL9dHKV5Lix85Azq.jpg\",\"original_language\":\"en\",\"original_title\":\"Emma.\",\"genre_ids\":[35,18],\"title\":\"Emma.\",\"vote_average\":7.6,\"overview\":\"In 1800s England, a well-meaning but selfish young woman meddles in the love lives of her friends.\",\"release_date\":\"2020-02-21\"},{\"popularity\":31.026,\"vote_count\":2,\"video\":false,\"poster_path\":\"\\/kTHzM6pPIjs4LHX33thyZpnKiOP.jpg\",\"id\":566927,\"adult\":false,\"backdrop_path\":\"\\/yQ2dPYSHcuTL8Ee4xSST0PmJ2jv.jpg\",\"original_language\":\"en\",\"original_title\":\"Impractical Jokers: The Movie\",\"genre_ids\":[35],\"title\":\"Impractical Jokers: The Movie\",\"vote_average\":8,\"overview\":\"The story of a humiliating high school mishap from 1992 that sends the Impractical Jokers on the road competing in hidden-camera challenges for the chance to turn back the clock and redeem three of the four Jokers.\",\"release_date\":\"2020-02-21\"},{\"popularity\":21.901,\"vote_count\":1,\"video\":false,\"poster_path\":\"\\/4J6RxvVCdgu7xRflDNsD492nsfi.jpg\",\"id\":565379,\"adult\":false,\"backdrop_path\":\"\\/gz8eQ8ysCcHIImHRvLr1mgx6Eot.jpg\",\"original_language\":\"en\",\"original_title\":\"Premature\",\"genre_ids\":[18,10749],\"title\":\"Premature\",\"vote_average\":2,\"overview\":\"The summer before she leaves for college, Ayanna meets handsome and mysterious outsider Isaiah; her entire world is turned upside down as she navigates the demanding terrain of young love against a changing Harlem landscape.\",\"release_date\":\"2020-02-21\"},{\"popularity\":21.939,\"vote_count\":2,\"video\":false,\"poster_path\":\"\\/xrlYLRoEtJb8FGKaoePPQHrWIHt.jpg\",\"id\":557940,\"adult\":false,\"backdrop_path\":null,\"original_language\":\"en\",\"original_title\":\"Las Pildoras de mi Novio\",\"genre_ids\":[],\"title\":\"My Boyfriend's Meds\",\"vote_average\":7,\"overview\":\"A Woman's island getaway with her boyfriend is thrown for a loop when he forgets to take his prescription medications along.\",\"release_date\":\"2020-02-21\"},{\"popularity\":34.903,\"vote_count\":276,\"video\":false,\"poster_path\":\"\\/jtrhTYB7xSrJxR1vusu99nvnZ1g.jpg\",\"id\":522627,\"adult\":false,\"backdrop_path\":\"\\/9Qfawg9WT3cSbBXQgDRuWbYS9lj.jpg\",\"original_language\":\"en\",\"original_title\":\"The Gentlemen\",\"genre_ids\":[28,35,80],\"title\":\"The Gentlemen\",\"vote_average\":7.8,\"overview\":\"American expat Mickey Pearson has built a highly profitable marijuana empire in London. When word gets out that he’s looking to cash out of the business forever it triggers plots, schemes, bribery and blackmail in an attempt to steal his domain out from under him.\",\"release_date\":\"2020-01-24\"},{\"popularity\":46.256,\"vote_count\":288,\"video\":false,\"poster_path\":\"\\/3NTEMlG5mQdIAlKDl3AJG0rX29Z.jpg\",\"id\":531428,\"adult\":false,\"backdrop_path\":\"\\/joXf2ToDZjVMBxWrzijQ3V9cC8p.jpg\",\"original_language\":\"fr\",\"original_title\":\"Portrait de la jeune fille en feu\",\"genre_ids\":[18,10749],\"title\":\"Portrait of a Lady on Fire\",\"vote_average\":8.3,\"overview\":\"On an isolated island in Brittany at the end of the eighteenth century, a female painter is obliged to paint a wedding portrait of a young woman.\",\"release_date\":\"2019-12-06\"},{\"popularity\":40.062,\"vote_count\":68,\"video\":false,\"poster_path\":\"\\/xssWHNrOhAeq0EUzHbHqn1SRb5s.jpg\",\"id\":548473,\"adult\":false,\"backdrop_path\":\"\\/8keFEE7XPTLEJ4hL67LZQGPHNCo.jpg\",\"original_language\":\"en\",\"original_title\":\"Color Out of Space\",\"genre_ids\":[27,878],\"title\":\"Color Out of Space\",\"vote_average\":6.6,\"overview\":\"The Gardner family moves to a remote farmstead in rural New England to escape the hustle of the 21st century. They are busy adapting to their new life when a meteorite crashes into their front yard, melts into the earth, and infects both the land and the properties of space-time with a strange, otherworldly colour. To their horror, the family discovers this alien force is gradually mutating every life form that it touches—including them.\",\"release_date\":\"2020-01-24\"},{\"popularity\":23.414,\"vote_count\":23,\"video\":false,\"poster_path\":\"\\/4e0Der73TmhAeA9grTo2Kkx0pAd.jpg\",\"id\":503917,\"adult\":false,\"backdrop_path\":\"\\/1wv5gjKrxaQKn6CyyMYcANY9soM.jpg\",\"original_language\":\"cn\",\"original_title\":\"肥龍過江\",\"genre_ids\":[28,35],\"title\":\"Enter the Fat Dragon\",\"vote_average\":4.9,\"overview\":\"After being dumped by his fiancé, heartbroken Hong Kong police officer Fallon Zhu gains 200+ pounds. His superiors demote him to the job of escorting convicts to Japan. When ha convict in his custody mysteriously dies, he must team up with citizen Thor to solve the mystery.\",\"release_date\":\"2020-02-14\"},{\"popularity\":13.116,\"vote_count\":1,\"video\":false,\"poster_path\":\"\\/wp8tShWPBT7kr8q8JeUX1ci0NXY.jpg\",\"id\":669384,\"adult\":false,\"backdrop_path\":null,\"original_language\":\"nl\",\"original_title\":\"Acquitted by Faith\",\"genre_ids\":[],\"title\":\"Acquitted by Faith\",\"vote_average\":1,\"overview\":\"Attorney Benjamin Stills is nearly sentenced to prison and finds faith in God after killing a teenage girl in an accidental car crash from texting while driving.\",\"release_date\":\"2020-02-20\"},{\"popularity\":33.598,\"vote_count\":11,\"video\":false,\"poster_path\":\"\\/cdqZqIcWt0Ne2Io2OA9iWqqMuCA.jpg\",\"id\":589049,\"adult\":false,\"backdrop_path\":\"\\/kU5qE5b6ksL3osaqKCUlMS08Ays.jpg\",\"original_language\":\"en\",\"original_title\":\"The Photograph\",\"genre_ids\":[18,10749],\"title\":\"The Photograph\",\"vote_average\":6.9,\"overview\":\"When famed photographer Christina Eames dies unexpectedly, she leaves her estranged daughter, Mae, hurt, angry and full of questions. When Mae finds a photograph tucked away in a safe-deposit box, she soon finds herself delving into her mother's early life -- an investigation that leads to an unexpected romance with a rising journalist.\",\"release_date\":\"2020-02-14\"},{\"popularity\":17.396,\"vote_count\":6,\"video\":false,\"poster_path\":\"\\/gpOJSrjIsSpZ1qZqxoUZ4YaWIOO.jpg\",\"id\":547012,\"adult\":false,\"backdrop_path\":\"\\/9lpIIpE6sBKCa1GcWrnapGk5l5l.jpg\",\"original_language\":\"en\",\"original_title\":\"Buffaloed\",\"genre_ids\":[35,18],\"title\":\"Buffaloed\",\"vote_average\":5.4,\"overview\":\"Set in the underworld of debt-collecting and follows the homegrown hustler Peg Dahl, who will do anything to escape Buffalo, NY.\",\"release_date\":\"2020-02-14\"}],\"page\":1,\"total_results\":101,\"dates\":{\"maximum\":\"2020-02-22\",\"minimum\":\"2020-01-05\"},\"total_pages\":6}"

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(result)
        )

        SystemClock.sleep(1000)

        onView(withId(R.id.rvMovieList))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvMovieList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        val textView = onView(
            Matchers.allOf(
                withId(R.id.movieDetailTitleTextView), withText("Sonic the Hedgehog"),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Sonic the Hedgehog")))

    }

    @After
    fun tearDown(){
        mockWebServer.close()
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(resource)
    }
}