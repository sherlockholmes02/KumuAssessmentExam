package com.kumu.assessmentexam.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
* Created by Darryl Dave P. de Castro on 12/02/2022.
*/

/*
*
* Movie object sample
* {
"wrapperType":"track",
"kind":"feature-movie",
"collectionId":1562034578,
"trackId":208510932,
"artistName":"Nicholas Meyer",
"collectionName":"Star Trek The Original Motion Pictures 6 Movies",
"trackName":"Star Trek II: The Wrath of Khan",
"collectionCensoredName":"Star Trek The Original Motion Pictures 6 Movies",
"trackCensoredName":"Star Trek II: The Wrath of Khan",
"collectionArtistId":1008915738,
"collectionArtistViewUrl":"https://itunes.apple.com/us/artist/paramount-home-entertainment-inc/1008915738?uo=4",
"collectionViewUrl":"https://itunes.apple.com/us/movie/star-trek-ii-the-wrath-of-khan/id208510932?uo=4",
"trackViewUrl":"https://itunes.apple.com/us/movie/star-trek-ii-the-wrath-of-khan/id208510932?uo=4",
"previewUrl":"https://video-ssl.itunes.apple.com/itunes-assets/Video128/v4/a7/e4/00/a7e400dc-0c4e-c337-0c05-2daecf1ff59e/mzvf_6213314798270891293.640x478.h264lc.U.p.m4v",
"artworkUrl30":"https://is3-ssl.mzstatic.com/image/thumb/Video125/v4/03/5f/2f/035f2f44-1d0e-99b4-6265-ba042dacb15d/pr_source.lsr/30x30bb.jpg",
"artworkUrl60":"https://is3-ssl.mzstatic.com/image/thumb/Video125/v4/03/5f/2f/035f2f44-1d0e-99b4-6265-ba042dacb15d/pr_source.lsr/60x60bb.jpg",
"artworkUrl100":"https://is3-ssl.mzstatic.com/image/thumb/Video125/v4/03/5f/2f/035f2f44-1d0e-99b4-6265-ba042dacb15d/pr_source.lsr/100x100bb.jpg",
"collectionPrice":12.99,
"trackPrice":12.99,
"trackRentalPrice":3.99,
"collectionHdPrice":14.99,
"trackHdPrice":14.99,
"trackHdRentalPrice":3.99,
"releaseDate":"1982-06-04T07:00:00Z",
"collectionExplicitness":"notExplicit",
"trackExplicitness":"notExplicit",
"discCount":1,
"discNumber":1,
"trackCount":6,
"trackNumber":2,
"trackTimeMillis":6793995,
"country":"USA",
"currency":"USD",
"primaryGenreName":"Sci-Fi & Fantasy",
"contentAdvisoryRating":"PG",
"longDescription":"It is the 23rd century. The Federation Starship U.S.S. EnterpriseTM is on routine training maneuvers and Admiral James T. Kirk (William Shatner) seems resigned to the fact that this inspection may well be the last space mission of his career. But Khan is back. Aided by his exiled band of genetic supermen, Khan (Ricardo Montalban) - brilliant renegade of 20th century Earth - has raided Space Station Regula One, stolen a top secret device called Project Genesis, wrested control of another Federation starship, and now schemes to set a most deadly trap for his old enemy Kirk... with the threat of a universal Armageddon!",
"hasITunesExtras":true
}
*
* */
@Entity(tableName = "medias")
data class Media(
    @PrimaryKey
    var trackId: Int,
    var trackName: String?,
    var kind: String?,
    var previewUrl: String,
    @SerializedName("artworkUrl100")
    var artwork: String,
    var trackPrice: Double?,
    var trackRentalPrice: Double?,
    var collectionHdPrice: Double?,
    var trackHdPrice: Double?,
    var trackHdRentalPrice: Double?,
    var releaseDate: String?,
    var longDescription: String?,
    @SerializedName("primaryGenreName")
    var genre: String?,
    var description: String?,
    var collectionName: String?,
    var collectionPrice: Double?,
    var trackTimeMillis: Long?,
)