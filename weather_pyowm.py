#coding: utf-8
import pyowm
import pandas as pd
import re
import os
import sys
from dateutil.parser import parse
owm = pyowm.OWM('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx')
town = pd.read_csv("town.csv",header=None)    #入力された都市名を「〇〇県〇〇市」に変換するための一覧を読み込む
city = pd.read_csv("city_jp_ja.csv", header=None)    #pyowmで使う都市のIDの一覧を読み込む
city_name = sys.argv
city_name = city_name[1]

for i in range(1,14):
	town_id = re.split(" +", str(town[town[i] == city_name]))
	if town_id[0] != "Empty":
		break
if town_id[0] == "Empty":
	print "日本に存在しない地域か、気象台が存在しない地域です"
	sys.exit()
city_id = re.split(" +", str(city[city[1] == town_id[-2]]))    #一覧から取得した情報をリスト化
try:
	obs_list = owm.weather_at_id(int(city_id[3]))     #都市IDから都市の情報を取得
	weather = obs_list.get_weather()    #取得した情報から天気情報を取得
	#print weather.get_temperature('celsius')   #気温とか
	weather_detail = weather.get_detailed_status()    #詳細な天気情報を取得
	taiou = pd.read_csv("weather_taiou.csv",encoding="SHIFT-JIS" , header=None)
	taiou_jp = re.split(" +", str(taiou[taiou[1] == weather_detail]))
	print city_id[-1]+" : "+str(taiou_jp[-1])
except:
	print town_id[-2]+" : 気象台が存在しない地域です"
