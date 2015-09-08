import sys
import os
import spotipy
import spotipy.util as util
import pprint

def main():

    # pretty print
    pp = pprint.PrettyPrinter(indent=4)

    # users
    users = ['hpatel13', 'wayspurrchen', 'allison.marie.hammond', '1258866041', '1230927257', 'matthewmaysonet', '121672770', '1219825601', 'rocksteady1313', '1240855449', 'djrebase', '1218843405', '1217235967', '1258548537', 'stetyldic', 'eddiemoney1', '125539967', 'thebandito', '123710650', 'allsop207', '124405909', '124954713', 'glory14', 'quinlanroyce', '1211188147', '121992691', '1226635310', 'catmandabomb', '1258925414', '129291504', '129833991', '122815469', 'mhanley24', 'sstamler', 'alexheiman', '121425691', '1242463522', '125809126', 'ruecritchfield', '1211822090', '1236500795', '1238460903', '127891280', 'mattyszucs', '1246917672', '122298282', '1254509207', '1220992694', '1242558506', '123277527', '123879796', 'ncolakhodzic', '1285470950', '1283658077', '1210178120', '1236558710', '1226179565', '126845875', '122183838', '125130791', '1162898832', '122027052', '122742594', '129369849', 'alexyee', '123481630', '1241859798', '121683424', '1224655375', '1212199050', '127486916', '126542648', '129456138', '121534226', 'claystewart', 'abigail.sellman', '1218148862', '1262756194', '1233291878', 'psant', 'r00sey', 'koalabluejay', '127499807', '1240201407', '1255601907', '124477988', '1211600533', '1238710040', '121486749', '1236382966', '1227405034', '1220596670', '1249385828', 'nsthorat', '1264990391', 'derk924', 'frwct1', '123306050', '1245921668', 'dboyd435', '1246182483', '1272625775', 'Epiphone353', 'elainelin', 'edvard_m', 'boogalooboo', '121103283', 'mcs1992', 'lmljoe', 'lizyeomans', 'assiagrazioli', 'lnards', 'magerleagues', 'karlashnikov', 'beengan', 'jonathan.tarlton']

    spotify = Spotify()
    # get playlists
    if spotify.sp:
        playlists = spotify.sp.user_playlists('hpatel13')
        pp.pprint(playlists)

class Spotify():
    client_id = '3b485568ef3340c586dd9b6eabde7f28'
    client_secret = '0b7935b526e64657b860fe5d755724f2'
    redirect_uri = 'http://localhost:8888/callback'
    scope = 'user-library-read'
    sp = None

    def __init__(self):
        # set environment variables
        os.environ['SPOTIPY_CLIENT_ID'] = self.client_id
        os.environ['SPOTIPY_CLIENT_SECRET'] = self.client_secret
        os.environ['SPOTIPY_REDIRECT_URI'] = self.redirect_uri
        self.sp = self.start_spotipy()

    def start_spotipy(self):
        if len(sys.argv) > 1:
            username = sys.argv[1]
        else:
            print("Usage: {0} username".format(sys.argv[0]))
            sys.exit()
        token = util.prompt_for_user_token(username, self.scope)
        if token:
            return spotipy.Spotify(auth=token)
        else:
            print("Can't get token for", username)



if __name__ == "__main__":
    main()

