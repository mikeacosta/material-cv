package net.acosta.mike.resume.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "job")
data class Job(@PrimaryKey(autoGenerate = false) val id: Int,
               val company: String,
               val title: String,
               val start: String,
               val end: String,
               val website: String,
               val image: String,
               val content: List<String>,
               var lastUpdate: Date?)

data class Jobs(val items: List<Job>)

@Entity(tableName = "content")
data class Content(@PrimaryKey(autoGenerate = true) var id: Long?,
                   val code: String,
                   val certMain: String,
                   val certSub: String,
                   val certAction: String,
                   val certSupport: String,
                   val eduMain: String,
                   val eduSub: String,
                   val eduSupport: String,
                   val github: String,
                   val web: String,
                   val email: String,
                   val googleCert: String,
                   val playStore: String,
                   var lastUpdate: Date?,
                   val msCertMain: String,
                   val msCertAction: String,
                   val msCert: String,
                   val api: String)

data class Profile(val items: List<ProfileItem>)

@Entity(tableName = "profile")
data class ProfileItem(@PrimaryKey(autoGenerate = true) var id: Long?,
                       val type: ProfileType,
                       val text: String,
                       var lastUpdate: Date?)

data class ApiCredentials(val username: String, val password: String)