package info.api.coin

import info.api.coin.configuration.Settings
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test
import io.restassured.response.Response




class BitcoinPriceTest: Settings() {

    private val apiKey = "5b2e870c-2204-41c5-96b9-4d6d18843d0e" // Замените на реальный ключ
//    private val baseUrl = "https://rest.coinapi.io"

    @Test
    fun `get assets endpoint should return 401 without api key`() {
        val response: Response = RestAssured.given()
            .baseUri(URL)
            .get("/v1/assets")

        response.statusCode shouldBe 401
    }

    @Test
    fun `get assets endpoint should return correct structure with valid api key`() {
//        // Этот тест будет пропущен, если нет API ключа
        if (apiKey == "5b2e870c-2204-41c5-96b9-4d6d18843d0e") return

        shouldNotThrowAny {
            val response: Response = RestAssured.given()
                .baseUri(URL)
                .header("X-CoinAPI-Key", apiKey)
                .get("/v1/assets")

            response.statusCode shouldBe 200
            response.jsonPath().getList<String>("asset_id").shouldBe(listOf("BTC", "ETH", "USD")) // Пример проверки
        }
    }

    @Test
    fun `get BTC price should be higher than 1000 USD`() {
        if (apiKey == "5b2e870c-2204-41c5-96b9-4d6d18843d0e") return

        shouldNotThrowAny {
            val rate = Given {
                baseUri(URL)
                header("X-CoinAPI-Key", apiKey)
            } When {
                get("/v1/exchangerate/BTC/USD")
            } Then {
                statusCode(200)
            } Extract {
                jsonPath().getFloat("rate")
            }

            rate shouldBeGreaterThan 1000f // Проверяем, что BTC стоит больше $1000
        }
    }

