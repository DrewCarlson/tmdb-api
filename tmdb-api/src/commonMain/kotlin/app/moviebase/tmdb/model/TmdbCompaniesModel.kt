package app.moviebase.tmdb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


object TmdbCompanyId {
    const val NETFLIX_INTERNATIONAL_PICTURES = 145174
}


@Serializable
data class TmdbCompany(
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val originCountry: String? = null // e. g. US
): TmdbSearchable

@Serializable
data class TmdbCompanyPageResult(
    @SerialName("page") override val page: Int,
    @SerialName("results") override val results: List<TmdbCompany> = emptyList(),
    @SerialName("total_results") override val totalResults: Int,
    @SerialName("total_pages") override val totalPages: Int,
) : TmdbPageResult<TmdbCompany>

@Serializable
data class TmdbCompanyDetail(
    @SerialName("id") val id: Int,
    @SerialName("headquarters") val headquarters: String,
    @SerialName("homepage") val homepage: String,
    @SerialName("logo_path") val logoPath: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val originCountry: String? = null // e. g. US
)
