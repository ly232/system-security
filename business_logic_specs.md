# login page #

The flow of login process is:

**server up running**

**client making a socket connection to server**

**client waits for user to chose from login or register**

> if (user chose login), then
> > client-->server: loginCred=XML(username, password)
> > server: checks loginCred against backend DB
> > > if (loginCred is valid),then
> > > > server-->client: client user's home board

> > > else (loginCred is invalid), then
> > > > if (client tried MAX\_TRY logins),then
> > > > > server shuts connection

> > > > server-->client: incorrect loginCred
> > > > client-->server: loginCred=XML(new\_username,new\_password)

> else (user chose register), then
> > client-->server: registCred=XML(vc,uid,pwd,name,location,role\_id)
> > server: checks if vc is valid (currently vc is hard-coded)
> > > if (vc is valid), then
> > > > server: checks if registCred.uid is already in DB
> > > > > if (registCred.uid is already in DB)
> > > > > > server-->client: uid already used

> > > > > else
> > > > > > server: insert registCred to user table in DB
> > > > > > server-->client: client user's home board

> > > else (vc is invalid), then
> > > > server-->client: invalid verification code
> > > > client: repeat registration procedure

  * validateUser(username,password)
  * userRegist(registInfo)
# self-board #
  * switchBoard(target\_username);
  * updateRegionInfo(RegionNumber, msg\_content);
# other person's board #
  * getRegionInfo(RegionNumberArr, target\_username, msg\_content);
  * postToRegion(RegionNumber, ta);

**ALL OUTPUTS ARE XML**