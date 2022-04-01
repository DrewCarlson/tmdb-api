package app.moviebase.tmdb.model

import app.moviebase.tmdb.remote.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TmdbShowStatus(val value: String) {
    @SerialName("Returning Series")
    RETURNING_SERIES("Returning Series"),

    @SerialName("In Production")
    IN_PRODUCTION("In Production"),

    @SerialName("Planned")
    PLANNED("Planned"),

    @SerialName("Canceled")
    CANCELED("Canceled"),

    @SerialName("Ended")
    ENDED("Ended"),

    @SerialName("Pilot")
    PILOT("Pilot");

    companion object {
        fun find(value: String?) = values().find { it.value == value }
    }
}

@Serializable
enum class TmdbShowType(val value: String) {
    @SerialName("Scripted")
    SCRIPTED("Scripted"),

    @SerialName("Reality")
    REALITY("Reality"),

    @SerialName("Documentary")
    DOCUMENTARY("Documentary"),

    @SerialName("News")
    NEWS("News"),

    @SerialName("Talk")
    TALK("Talk"),

    @SerialName("Talk Show")
    TALK_SHOW("Talk Show"),

    @SerialName("Show")
    SHOW("Show"),

    @SerialName("Miniseries")
    MINISERIES("Miniseries");

    companion object {
        fun find(value: String?) = values().find { it.value == value }
    }
}


@Serializable
data class TmdbShowDetail(
    @SerialName("id") override val id: Int,
    val name: String,
    @SerialName("poster_path") override val posterPath: String?,
    @SerialName("backdrop_path") override val backdropPath: String?,
    val popularity: Float,
    @SerialName("first_air_date") @Serializable(LocalDateSerializer::class) val firstAirDate: LocalDate? = null,
    @SerialName("last_air_date") @Serializable(LocalDateSerializer::class) val lastAirDate: LocalDate? = null,
    @SerialName("genres") val genres: List<TmdbGenre>,
    @SerialName("last_episode_to_air") val lastEpisodeToAir: TmdbEpisode? = null,
    @SerialName("next_episode_to_air") val nextEpisodeToAir: TmdbEpisode? = null,
    @SerialName("number_of_episodes") val numberOfEpisodes: Int,
    @SerialName("number_of_seasons") val numberOfSeasons: Int,
    @SerialName("episode_run_time") val episodeRuntime: List<Int>,
    @SerialName("production_companies") val productionCompanies: List<TmdbCompany>? = null,
    val homepage: String? = null,
    @SerialName("in_production") val inProduction: Boolean,
    val seasons: List<TmdbSeason>,
    val networks: List<TmdbNetwork>,
    val status: TmdbShowStatus,
    val type: TmdbShowType,
    val languages: List<String>,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    val overview: String,
    val tagline: String,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("external_ids") val externalIds: TmdbExternalIds? = null,
    @SerialName("watch/providers") val watchProviders: TmdbProviderResult? = null,
    @SerialName("credits") val credits: TmdbCredits? = null,
    @SerialName("aggregate_credits") val aggregateCredits: TmdbAggregateCredits? = null,
    @SerialName("videos") val videos: TmdbResult<TmdbVideo>? = null,
    @SerialName("content_ratings") val contentRatings: TmdbResult<TmdbContentRating>? = null,
) : TmdbAnyMedia, TmdbBackdropMedia, TmdbPosterMedia

fun TmdbResult<TmdbContentRating>.getContentRating(country: String): String? =
    results.firstOrNull { it.iso3166 == country }?.rating

@Serializable
data class TmdbSeason(
    override val id: Int,
    @SerialName("air_date") @Serializable(LocalDateSerializer::class) val airDate: LocalDate? = null,
    @SerialName("episode_count") val episodeCount: Int? = null,
    val name: String,
    @SerialName("poster_path") override val posterPath: String?,
    @SerialName("season_number") val seasonNumber: Int,
    val episodes: List<TmdbEpisode>? = null,
    @SerialName("overview") val overview: String? = null,
) : TmdbAnyMedia, TmdbPosterMedia {

    val numberOfEpisodes get() = episodeCount ?: episodes?.size ?: 0
}

@Serializable
data class TmdbSeasonDetail(
    @SerialName("id") override val id: Int,
    @SerialName("air_date") @Serializable(LocalDateSerializer::class) val airDate: LocalDate? = null,
    @SerialName("episode_count") val episodeCount: Int? = null,
    val name: String,
    @SerialName("poster_path") override val posterPath: String?,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("overview") val overview: String,
    val episodes: List<TmdbEpisode>? = null,
    @SerialName("external_ids") val externalIds: TmdbExternalIds,
    @SerialName("videos") val videos: TmdbResult<TmdbVideo>,
    @SerialName("images") val images: TmdbResult<TmdbImages>,
) : TmdbAnyMedia, TmdbPosterMedia {

    val numberOfEpisodes get() = episodeCount ?: episodes?.size ?: 0

}

@Serializable
data class TmdbEpisode(
    @SerialName("id") override val id: Int,
    @SerialName("episode_number") val episodeNumber: Int,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("air_date") @Serializable(LocalDateSerializer::class) val airDate: LocalDate? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("vote_average") val voteAverage: Float? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("still_path") val stillPath: String? = null,
    @SerialName("overview") val overview: String? = null,
) : TmdbAnyMedia, TmdbBackdropMedia {

    override val backdropPath: String? get() = stillPath

}


@Serializable
data class TmdbEpisodeDetail(
    @SerialName("id") override val id: Int,
    @SerialName("overview") val overview: String,
    @SerialName("episode_number") val episodeNumber: Int,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("air_date") @Serializable(LocalDateSerializer::class) val airDate: LocalDate? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("vote_average") val voteAverage: Float? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("still_path") val stillPath: String? = null,
    @SerialName("images") val images: TmdbResult<TmdbImages>,
    @SerialName("crew") val crew: List<TmdbCrew>,
    @SerialName("guest_stars") val guestStars: List<TmdbCast>,
    @SerialName("external_ids") val externalIds: TmdbExternalIds,
) : TmdbAnyMedia, TmdbBackdropMedia {
    override val backdropPath: String? get() = stillPath
}

@Serializable
data class TmdbContentRating(
    @SerialName("iso_3166_1") val iso3166: String,
    @SerialName("rating") val rating: String
)
