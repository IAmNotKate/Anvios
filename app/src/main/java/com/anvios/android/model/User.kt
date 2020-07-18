package com.anvios.android.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val userId: Int,
    val username: String
) : Parcelable {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(userId)
        dest?.writeString(username)
    }

    override fun describeContents(): Int = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId
        result = 31 * result + username.hashCode()
        return result
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<User?> {
            override fun createFromParcel(source: Parcel?): User? {
                return if (source != null) {
                    User(
                        source.readInt(),
                        source.readString()!!
                    )
                } else {
                    null
                }
            }

            override fun newArray(size: Int): Array<User?> = newArray(size)
        }
    }
}