import requests
import json

catch = {}
from_ = input().lower()
r = requests.get(f"http://www.floatrates.com/daily/{from_}.json")
if r:
	dir = json.loads(r.text)
	catch["usd"] = dir.get("usd", 1)
	catch["eur"] = dir.get("eur", 1)
	while True:
		to_ = input().lower()
		if to_ == "":
			break
		while True:
			global amount
			amount = input()
			if amount.isnumeric():
				amount = int(amount)
				break
			else:
				continue
		print("Checking the cache...")
		if to_ in catch:
			print("Oh! It is in the cache!")
			print(f"You received {round(amount * catch[to_]['rate'], 2)} {to_.upper()}")
		else:
			print("Sorry, but it is not in the cache!")
			print(f"You received {round(amount * dir[to_]['rate'], 2)} {to_.upper()}")
			catch[to_] = dir[to_]
else:
	print("Error")
