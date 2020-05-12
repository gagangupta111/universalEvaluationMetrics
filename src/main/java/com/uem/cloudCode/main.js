
Parse.Cloud.beforeSave(Parse.TEST, function(request, response) {

	try {

        if (request.object.isNew()) {
            request.object.unset("dummy");
            request.object.set("col4", "cloudCodeDummyValue");
        }else{
            request.object.unset("dummy");
            request.object.set("col4", "cloudCodeDummyValue");
        }
        console.log("beforeSave TEST");
	/*	if (!request.object.dirty("col4")) {
			// response.error("E-Mail is required for Sign-Up.");
			// return;
		}else{
		    //
		}
    */

        /*
		var test = Parse.Object.extend("TEST");
		var query = new Parse.Query(test);
		query.equalTo("col1", request.object.get("col1"));
		query.first({useMasterKey: true,
			success: function(object) {
				if (object) {
					response.error("This col1 already exists.");
					return;
				} else {
					console.log("Col1 has unique value!");
					response.success();
					return;
				}
			},
			error: function(error) {
				response.error("Could not validate col1 for Object TEST.");
				return;
			}

		});
        */

	} catch (e) {
		response.error("500|Interval Server Error. Please Contact the Support Team.");
		return;
	}

	return;

});

Parse.Cloud.afterSave(Parse.TEST, function(request, response) {
    // after Save.
    console.log("afterSave TEST");
     if (request.object.isNew()) {
                request.object.unset("dummy");
                request.object.set("col4", "cloudCodeDummyValue");
            }else{
                request.object.unset("dummy");
                request.object.set("col4", "cloudCodeDummyValue");
            }
            return;
});

Parse.Cloud.define("getAllTEST", function(request, response){
    return;
});
