// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function (request, response) {
    console.log("MUCH HAMSTER");
    response.success("Hello world!");
});



Parse.Cloud.job("logTest", function (request, status) {


    console.log("HAMSTERRRRRR");
    status.success();

});