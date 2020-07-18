package com.anvios.android.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Post(
    val postId: Int,
    val title: String,
    val text: String,
    val dateCreated: Calendar,
    val user: User
) : Parcelable {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(postId)
        dest?.writeString(title)
        dest?.writeString(text)
        dest?.writeSerializable(dateCreated)
        dest?.writeParcelable(user, flags)
    }

    override fun describeContents(): Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (postId != other.postId) return false
        if (title != other.title) return false
        if (text != other.text) return false
        if (dateCreated != other.dateCreated) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postId
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + dateCreated.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Post?> {
            override fun createFromParcel(source: Parcel?): Post? {
                return if (source != null) {
                    Post(
                        source.readInt(),
                        source.readString()!!,
                        source.readString()!!,
                        source.readSerializable() as Calendar,
                        source.readParcelable(User::class.java.classLoader)!!
                    )
                } else {
                    null
                }
            }

            override fun newArray(size: Int): Array<Post?> = newArray(size)
        }
    }
}