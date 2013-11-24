#Get and display the workorder name
wonum = scriptHome.getString("wonum")
print "Work order identifier: ", wonum

#Get and display the value for the WO spec SPEED (if it exists)
woSpecSet = scriptHome.getMboSet("$SPECS", "WORKORDERSPEC", "assetattrid='SPEED'")
emptySet = woSpecSet.isEmpty()

if emptySet == 0:
   woSpec = woSpecSet.getMbo(0)
   speed = woSpec.getInt("numvalue")
   units = woSpec.getString("measureunitid")
   print "Speed = ", speed, units

#Modify the owner attribute of the object
scriptHome.setValue("owner", "WILSON")
owner = scriptHome.getString("owner")
print "New owner is " , owner

#Get workflow instance spec for attribute MATERIAL. Display the value, then modify it
instSpecSet = wfinstance.getMboSet("$INSTSPECS", "WFINSTANCESPEC", "assetattrid='MATERIAL'")
emptySet = instSpecSet.isEmpty()

if emptySet == 0:
   instSpec = instSpecSet.getMbo(0)
   material = instSpec.getString("alnvalue")
   print "Material: ", material  
   instSpec.setValue("alnvalue", "COPPER")
   material = instSpec.getString("alnvalue")
   print "New Material: ", material