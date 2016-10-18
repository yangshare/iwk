var timers  = new Array;
var images  = new Array;
function changeThumb( id, url )
{
        document.getElementById(id).src = url;
}
function thumb_path( vid ) {
	var index = parseInt( (vid - 1) / max_thumb_folders );
	var tmb_folder = 'tmb';
	if ( index !== 0 ) {
		tmb_folder = 'tmb'+ index;
	}
	var path = cdn_url + '/media/videos/' + tmb_folder;
    return path;
}
$(document).ready(function() {
    $("body").on('mouseover', "img[id*='rotate_']", function(event) {
        var image_id    = $(this).attr("id");
        var id_split    = image_id.split('_');
        var video_id    = id_split[1];
		var thumbs		= id_split[2];
		if (typeof thumbs == "undefined") {
			thumbs = 20;
		}
		
        for ( var i=1; i<=thumbs; i++ ) {
            var image_url = thumb_path(video_id) + '/' + video_id + '/' + i + '.jpg';
            images[i]     = new Image();
            images[i].src = image_url;
        }
        for ( var i=1; i<=thumbs; i++ ) {
            timers[i] = setTimeout("changeThumb('" + image_id + "','" + thumb_path(video_id) + '/' + video_id + '/' + i + '.jpg' + "')", i*50*10);
        }
    }).on('mouseout', "img[id*='rotate_']", function(event) {
        var image_id    = $(this).attr("id");
        var id_split    = image_id.split('_');
        var video_id    = id_split[1];
		var thumbs		= id_split[2];
		var def_thumb = id_split[3];		
		if (typeof thumbs == "undefined") {
			thumbs = 20;
		}

        for ( var i=1; i<=thumbs; i++ ) {
            if ( typeof timers[i] == "number" ) {
                clearTimeout(timers[i]);
            }
        }
		if ( $.isNumeric(def_thumb) )
			$(this).attr('src', thumb_path(video_id) + '/' + video_id + '/' + def_thumb + '.jpg');
		else
			$(this).attr('src', thumb_path(video_id) + '/' + video_id + '/1.jpg');
    });
});
