package *****[INSERT YOUR PACKAGE NAME HERE]*****

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentUtil {

    companion object {
        fun getAllComments(): ArrayList<Comment> {
            val listOfComments = mutableListOf<Comment>()
            val jsonArray = JSONArray(commentJsonArray)
            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    listOfComments.add(Comment(
                            getLong("id"),
                            getString("firstName"),
                            getString("lastName"),
                            getString("username"),
                            getString("comment"),
                            getString("date").toLong()
                    ))
                }
            }
            listOfComments.sortWith( compareBy { it.date } )
            return ArrayList(listOfComments)
        }

        fun createComment(firstName: String, lastName: String, username: String, comment: String): Comment {
            val timeEpoch = System.currentTimeMillis()
            return Comment(timeEpoch,
                    firstName,
                    lastName,
                    username,
                    comment,
                    timeEpoch,
                    false
            )
        }

        private val commentJsonArray = """
            [{"id":1,"firstName":"Grove","lastName":"MacAndrew","username":"gmacandrew0","comment":"Extended directional concept","date":"1580344216"},
            {"id":2,"firstName":"Fenelia","lastName":"Harby","username":"fharby1","comment":"Operative multi-tasking interface","date":"1572199825"},
            {"id":3,"firstName":"Fransisco","lastName":"Dutnall","username":"fdutnall2","comment":"Fully-configurable zero tolerance neural-net","date":"1576095992"},
            {"id":4,"firstName":"Marigold","lastName":"Roels","username":"mroels3","comment":"Compatible 24 hour info-mediaries","date":"1583954491"},
            {"id":5,"firstName":"Ella","lastName":"Deniseau","username":"edeniseau4","comment":"Quality-focused hybrid utilisation","date":"1572308586"},
            {"id":6,"firstName":"Nathanael","lastName":"Galea","username":"ngalea5","comment":"Ergonomic non-volatile framework","date":"1573355650"},
            {"id":7,"firstName":"Dannie","lastName":"Lawty","username":"dlawty6","comment":"User-friendly human-resource moratorium","date":"1570761816"},
            {"id":8,"firstName":"Blake","lastName":"Milington","username":"bmilington7","comment":"Streamlined fresh-thinking array","date":"1580479844"},
            {"id":9,"firstName":"Valenka","lastName":"Cantor","username":"vcantor8","comment":"Visionary grid-enabled matrices","date":"1577113892"},
            {"id":10,"firstName":"Hatti","lastName":"Desaur","username":"hdesaur9","comment":"Distributed content-based portal","date":"1582414922"},
            {"id":11,"firstName":"Tobe","lastName":"Curr","username":"tcurra","comment":"Business-focused next generation knowledge base","date":"1576379328"},
            {"id":12,"firstName":"Purcell","lastName":"Landsman","username":"plandsmanb","comment":"Extended tangible firmware","date":"1571827965"},
            {"id":13,"firstName":"Warren","lastName":"Brabbins","username":"wbrabbinsc","comment":"Function-based incremental process improvement","date":"1575763382"},
            {"id":14,"firstName":"Orin","lastName":"Bearblock","username":"obearblockd","comment":"Ameliorated 24/7 installation","date":"1576987981"},
            {"id":15,"firstName":"Cassie","lastName":"Stowgill","username":"cstowgille","comment":"Fully-configurable attitude-oriented groupware","date":"1583430527"},
            {"id":16,"firstName":"Blythe","lastName":"Gilvear","username":"bgilvearf","comment":"Implemented dedicated task-force","date":"1583553583"},
            {"id":17,"firstName":"Benedikta","lastName":"McRory","username":"bmcroryg","comment":"Business-focused maximized secured line","date":"1580874861"},
            {"id":18,"firstName":"Dorie","lastName":"Croney","username":"dcroneyh","comment":"Re-engineered national orchestration","date":"1579726592"},
            {"id":19,"firstName":"Chas","lastName":"Fishlee","username":"cfishleei","comment":"Configurable heuristic interface","date":"1570975919"},
            {"id":20,"firstName":"Tamar","lastName":"Clem","username":"tclemj","comment":"Reverse-engineered value-added solution","date":"1577838962"}]
        """.trimIndent()
    }
}

class EasyDateUtil {
    companion object {
        fun epochToDateString(epoch: Long): String {
            return try {
                val simpleDateFormat = SimpleDateFormat("MM/dd/yy", Locale.US)
                val date = Date(epoch * 1000)
                simpleDateFormat.format(date)
            } catch (e: Exception) {
                e.toString()
            }
        }
    }
}

data class Comment(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val username: String,
        val comment: String,
        val date: Long,
        var isLiked: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readLong(),
            parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeLong(id)
        writeString(firstName)
        writeString(lastName)
        writeString(username)
        writeString(comment)
        writeLong(date)
        writeByte(if (isLiked) 1 else 0)
    }
    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment = Comment(parcel)
        override fun newArray(size: Int): Array<Comment?> =  arrayOfNulls(size)
    }
}