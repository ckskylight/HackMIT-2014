// Require and initialize the Twilio module with your credentials
var client = require('twilio')('ACc29b02216c3175cdff08c17e54c5cbd4', 'de384b6e24963d6f0fe74f2b64ed6787');


Parse.Cloud.define("sendSMS", function (request, response) {
    console.log("Sent a new text!");
    console.log(client);
    // Send an SMS message
    client.sendSms({
        to: '15202618230',
        from: '15203086570',
        body: 'Hello world!'
    }, function (err, responseData) {
        if (err) {
            console.log(err);
            response.error("Fail :(");
        } else {
            console.log(responseData.from);
            console.log(responseData.body);
            response.success("jhjh");
        }
    });
});

Parse.Cloud.define("receiveSMS", function (request, response) {
    console.log("haz begun");
    var helpStartSeq = "HelpMe";
    var shelterStartSeq = "HelpOthers";
    var Mouse = Parse.Object.extend("Mouse");
    console.log("a");
    var newEntry = new Mouse();
    console.log("b");
    var splitText = request.params.Body.split(".");
    console.log(splitText);
    if (splitText[0] == helpStartSeq) {
        console.log("case 1");
        try {
            newEntry.set("Number", request.params.From);
            newEntry.set("Name", splitText[1]);
            newEntry.set("Location", splitText[3]);
        } catch (err) {
            console.log("Error");
        }
        try {
            newEntry.set("SmartPhone", splitText[2]);
            console.log("case 3");

        } catch (err) {
            newEntry.set("SmartPhone", splitText[2]);
            console.log("case 4");

        }
        try {
            newEntry.set("HostAddress", splitText[3]);
            newEntry.set("HostCapacity", splitText[4]);
            console.log("case 5");

        } catch (err) { 
            newEntry.set("HostAddress", "NotAHost");
            newEntry.set("HostCapacity", "NotAHost");
            console.log("case 6");
        }
        try {

            newEntry.set("Official", splitText[5]);
            console.log("case 7");

        } catch (err) {
            newEntry.set("Official", false);
            console.log("case 8");
        }
        //Number, Name, Location, SmartPhone(Bool), Host Address, Host Capacity, Host (Bool)
    } else if (splitText[0] == shelterStartSeq) {
        console.log("case 9");

    } else {
        console.log("case 10");
    }
    /*
  else {
      Parse.Cloud.run("sendSMS", {
        to:'15202618230', 
        from: '15203086570', 
        body: 'Your formatting was wrong! :( Use:'
      });
      
  }*/

    newEntry.save(null, {
        success: function (newEntry) {
            //alert("HAMSTER COMPLET ");
            console.log("case blagh");
            response.success("hamster");

        },
        error: function (newEntry, error) {
            //alert("HAMSTER INCOMPLET");
            console.log("case blagggggh");
            response.error("!hamster");
        }
    });

    // User's location
    var userGeoPoint = splitText[2];
    // Create a query for places
    var query = new Parse.Query(userGeoPoint);
    // Interested in locations near user.
    query.near("Location", userGeoPoint);
    // Limit what could be a lot of points.
    query.limit(10);
    // Final list of objects
    query.find({
        success: function (placesObjects) {}
    });

    for (var i=0; i<userGeoPoint.length; i++){
        destinationAddress = userGeoPoint[i];
        var URLString = "https://maps.googleapis.com/maps/api/directions/json?origin=" + 42.356 + "," + -71.096 + "&destination=" + "410 Memorial Drive Cambridge, MA" + "&key=" + "AIzaSyD7k5RekuUfdp4iqiXb0I-kjaxVBzLVzdo" + "&mode=" + "walking";
        console.log(URLString);
        console.log(Parse.Cloud.httpResponse(URLString).text);
        var Mouse = Parse.Object.extend("");
        console.log("a");
        var newEntry = new Mouse();
        
        
    }
   // String URLString = "https://maps.googleapis.com/maps/api/directions/json?origin=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&destination=" + destinationAddress + "&key=" + directionsKey;

    //NORI'S CODE SO BEWARE!!!!!
    
    function getPage(request, response) { response.success("Hello world!") }

    Parse.Cloud.define("getPage", function(request, response) {
    
        Parse.Cloud.httpRequest({
            url: URLString,
            success: function(httpResponse) {
                console.log(httpResponse.text)
            },
            error: function(httpResponse) {
                console.error('Request failed with response code ' + httpResponse.status)
            }
        });
    

    });
});
//String URLString = "https://maps.googleapis.com/maps/api/directions/json?origin=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&destination=" + destinationAddress + "&key=" + directionsKey;