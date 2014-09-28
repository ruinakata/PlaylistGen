package makers

import com.wrapper.spotify.Api
import scala.collection.JavaConverters._



object App {
  val api = Api.builder()
    .clientId("276a56a316944e23a41d97b6b1895fdf")
    .clientSecret("cf8e711f318f4b2a96c53f37b5310e02")
    .build()
  val fbFriends = List("ruinakata", "hpatel13", "wayspurrchen", "allison.marie.hammond", "1258866041", "1230927257", "matthewmaysonet", "121672770", "1219825601", "rocksteady1313", "1240855449", "djrebase", "1218843405", "1217235967", "1258548537", "stet", "yldic", "eddiemoney1", "125539967", "thebandito", "123710650", "allsop207", "124405909", "124954713", "glory14", "quinlanroyce", "1211188147", "121992691", "1226635310", "catmandabomb", "1258925414", "129291504", "129833991", "122815469", "mhanley24", "ssta", "mler", "alexheiman", "121425691", "1242463522", "125809126", "ruecritchfield", "1211822090", "1236500795", "1238460903", "127891280", "mattyszucs", "1246917672", "122298282", "1254509207", "1220992694", "1242558506", "123277527", "123879796", "ncolakhodz", "ic", "1285470950", "1283658077", "1210178120", "1236558710", "1226179565", "126845875", "122183838", "125130791", "1162898832", "122027052", "122742594", "129369849", "alexyee", "123481630", "1241859798", "121683424", "1224655375", "1212199050", "127486916", "126542648", "129456138", "121534226", "claystewart", "abigail.sellman", "1218148862", "1262756194", "1233291878", "psant", "r00sey", "koalabluejay", "127499807", "1240201407", "1255601907", "124477988", "1211600533", "1238710040", "121486749", "123638296", "6", "1227405034", "1220596670", "1249385828", "nsthorat", "1264990391", "derk924", "frwct1", "123306050", "1245921668", "dboyd435", "1246182483", "1272625775", "Epiphone353", "elainelin", "edvard_m", "boogalooboo", "121103283", "mcs1992", "lmljoe", "lizyeomans", "assiagrazioli", "lnards", "magerleagues", "karlashnikov", "beengan", "jonathan.tarlton")
  val tasteMakers = List("spotify", "elainelin", "edvard_m", "boogalooboo", "121103283", "mcs1992", "lmljoe", "lizyeomans", "assiagrazioli", "lnards", "magerleagues", "karlashnikov", "beengan", "jonathan.tarlton")
  val universe = fbFriends ++ tasteMakers

  def main(args: Array[String]) {
    val request = api.clientCredentialsGrant().build.get()
    api.setAccessToken(request.getAccessToken)
    val users = universe.map(x =>
        try {
          Some(User(api, x))
        } catch {
          case _ => None
        }
      )
      .flatten
      .map(_.toJSON)
      .mkString(",")
    println("[" + users+ "]")

    //val users = universe.map { u =>
    //  val user = User(u)
    //  playlists.foreach(x => println(x.getName))
    //  playlists
    //}

    //val user = User(api, "ruinakata")

    //playlists.foreach(println)
    //println(playlists.length)
    //println(universe.length)
    //println(user.info.id + " " + user.info.name)
    //println(user.playlists.length)
    //println(user.toJSON)
  }
}
