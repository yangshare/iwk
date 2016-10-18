var post_timers         = new Array();
function destroy( selector )
{
    $(selector).hide();
}

function user_posting(selector, content, notimeout)
{
    $(selector).removeClass().addClass('posting');
    $(selector).html(content);
    $(selector).show();
    if (typeof notimeout=="undefined") {
        if ( typeof post_timers[0] == "number" ) {
            clearTimeout(post_timers[0]);
      }
      post_timers[0] = setTimeout("destroy('" + selector + "')", 3000);
    }
}

function user_posting_load(selector, content)
{
    user_posting(selector, content, 1);
}

function user_response(selector, content)
{
    $(selector).removeClass().addClass('response');
    $(selector).html(content);
    $(selector).show();
    if ( typeof post_timers[0] == "number" ) {
        clearTimeout(post_timers[0]);
    }
    post_timers[0] = setTimeout("destroy('" + selector + "')", 5000);
}

function reset_chars_counter()
{
    $('#chars_left').html('1000');
}

function insert_media(type, page)
{    
    var media_content   = $('#media_content');
    user_posting('#media_message', lang_loading);
    $.post(base_url + '/ajax/insert_' + type, { page: page },
    function (response) {
        if ( response.status == 0 ) {
            user_posting('#media_message', response.msg);
        } else {
            $(media_content).html(response.code);
            $(media_content).fadeIn();
        }
    }, 'json');
}

$(document).ready(function(){
    $("textarea[id*='_comment']").keyup(function(){
        var textarea_id = $(this).attr('id');
        var chars_left  = 1000 - $("textarea[id='" + textarea_id + "']").val().length;
        if ( chars_left < 0 ) {
            chars_left = 0;
        }
        $("span[id='chars_left']").html(chars_left);
    });

    $("#advanced_search").click(function(event){
        event.preventDefault();
        $("img[id='loading_advanced_search']").show();
        var search_type = $("select[id='search_type']").val();
        $.post(base_url + '/ajax/search', { search_type: search_type },
            function(response) {
                if ( response != '' ) {
                    $('#advanced_search_container').html(response);
                    $("#search_advanced").slideDown();
                }
        });
        $("img[id='loading_advanced_search']").hide();
    });

    $("body").on('click', "a[id*='search_tab_']", function(event) {
        event.preventDefault();
        var tab_clicked = $(this).attr('id');
        var tabs        = $("a[id*='search_tab_']");
        $.each(tabs, function() {
            var tab_current = $(this).attr('id');
            var tab_split   = tab_current.split('_');
            var search_type = tab_split[2];
            var container   = '#search_' + search_type;
            if ( tab_current == tab_clicked ) {
                $("select[id='search_type']").val(search_type);
                $("h2[id='advanced_search_title']").html('ADVANCED ' + search_type.toUpperCase() + ' SEARCH');
                $(container).show();
                $('#' + tab_current).removeClass().addClass('active');
            } else {
                $('#' + tab_current).removeClass();
                $(container).hide();
            }
        });
    });

    $("body").on('click', "#close_advanced_search", function(event) {
        event.preventDefault();
        $("#search_advanced").slideUp();
    });

    $("body").on('change', "select[id='search_type']", function(event) {    
        var search_type = $("select[id='search_type']").val();
        var tab_clicked = 'search_tab_' + search_type;
        var tabs        = $("a[id*='search_tab_']");
        $("h2[id='advanced_search_title']").html('ADVANCED ' + search_type.toUpperCase() + ' SEARCH');
        $.each(tabs, function() {
            var tab_current = $(this).attr('id');
            var tab_split   = tab_current.split('_');
            var container   = '#search_' + tab_split[2];
            if ( tab_current == tab_clicked ) {
                $(container).show();
                $('#' + tab_current).removeClass().addClass('active');
            } else {
                $('#' + tab_current).removeClass();
                $(container).hide();
            }            
        });
    });
    
    $("body").on('click', "a[id*='attach_media_']", function(event) {
        event.preventDefault();
        var attach_id   = $(this).attr('id');
        var id_split    = attach_id.split('_');
        var media_type  = id_split[2];
        var media_id    = id_split[3];
        var attach_str  = '[' + media_type + '=' + media_id + ']';        
        var txtarea     = $("textarea[id='blog_content']");
        if ( $(txtarea).length ) {
            $.markItUp({ replaceWith: attach_str });
        } else {
            if ( $("textarea[id='photo_comment']").length ) {
                var txtbox = $("textarea[id='photo_comment']");
            } else if ( $("textarea[id='video_comment']"). length ) {
                var txtbox = $("textarea[id='video_comment']");
            } else if ( $("textarea[id='wall_comment']").length ) {
                var txtbox = $("textarea[id='wall_comment']");
            } else if ( $("textarea[id='blog_comment']") ) {
                var txtbox = $("textarea[id='blog_comment']");
            }
            
            $(txtbox).val($(txtbox).val() + attach_str);
        }
        $('#media_content').fadeOut(); 
    });

    $("body").on('click', "a[id*='attach_mcp_']", function(event) {
        event.preventDefault();
        var click_id    = $(this).attr('id');
        var id_split    = click_id.split('_');
        var type        = id_split[2] + '_' + id_split[3];
        insert_media(type, 1);
    });

    $("body").on('click', "a[id*='p_mc_']", function(event) {    
        event.preventDefault();
        var page_id     = $(this).attr('id');
        var page_split  = page_id.split('_');
        var type        = page_split[2] + '_' + page_split[3];
        var page        = page_split[4];
        insert_media(type, page);
    });
    
    $("body").on('click', "a[id*='delete_comment_']", function(event) {
        event.preventDefault();
        var click_id    = $(this).attr('id');
        var id_split    = click_id.split('_');
        var type        = id_split[2];
        var comment_id  = id_split[3];
        var parent_id   = id_split[4];
        user_posting('#delete_response_' + comment_id, lang_deleting);
        $.post(base_url + '/ajax/' + type + '_comment_delete', { comment_id: comment_id, parent_id: parent_id },
        function(response) {
            if ( response.status == 0 ) {
                user_posting('#delete_response_' + comment_id, response.msg);
            } else {
                if ( type == 'wall' ) {
                    $('#wall_comment_' + comment_id).fadeOut();
					$('#end_num').html(parseInt($('#end_num').html(), 10)-1);
					$('#total_comments').html(parseInt($('#total_comments').html(), 10)-1);
                } else {
                    $('#' + type + '_comment_' + parent_id + '_' + comment_id).fadeOut();
					$('#end_num').html(parseInt($('#end_num').html(), 10)-1);
					$('#total_comments').html(parseInt($('#total_comments').html(), 10)-1);	
					if ( type == 'video' ) {
						$('#total_video_comments').html(parseInt($('#total_video_comments').html(), 10)-1);	
					}
                }
            } 
        }, 'json');
    });
    
     $("body").on('click', "a[id*='report_spam_']", function(event) {
        event.preventDefault();
        var click_id    = $(this).attr('id');
        var id_split    = click_id.split('_');
        var type        = id_split[2];
        var comment_id  = id_split[3];
        var parent_id   = id_split[4];
        $.post(base_url + '/ajax/report_spam', { type: type, comment_id: comment_id, parent_id: parent_id },
        function(response) {
            if ( response.status == 0 ) {
                user_posting('#delete_response_' + comment_id, response.msg);
            } else {
                $("span[id='reported_spam_" + comment_id + "_" + parent_id + "']").html(response.msg);
            }
        }, 'json');
    });
    
    $("a[id*='delete_photo_']").click(function(event) {
        event.preventDefault();
        var delete_confirm  = confirm(lang_delete_photo_ask);
        if ( !delete_confirm ) {
            return false;
        }		
        var click_id    = $(this).attr('id');
        var id_split    = click_id.split('_');
        var photo_id    = id_split[2];
        user_posting('#delete_photo_message', lang_deleting);
        $.post(base_url + '/ajax/delete_photo', { photo_id: photo_id },
        function(response) {
            if ( response.status == 0 ) {
                user_posting('#delete_photo_message', response.msg);
            } else {
                user_response('#delete_photo_message', response.msg);
                $('#album_photo_' + photo_id).fadeOut();
            }
        }, 'json');
    });

    $("input[id*='send_share_']").click(function(event) {
        event.preventDefault();
        var errors          = false;
        var share_id        = $(this).attr('id');
        var id_split        = share_id.split('_');
        var type            = id_split[2];
        var item_id         = id_split[3];
        var share_from      = $("input[id='share_from']").val();
        var share_to        = $("textarea[id='share_to']").val();
        var share_message   = $("textarea[id='share_message']").val();
        var share_from_err  = $("div[id='share_from_error']");
        var share_to_err    = $("div[id='share_to_error']");
        if ( share_from == '' ) {
            errors = true;
            $(share_from_err).html(lang_share_name_empty);
            $(share_from_err).fadeIn();
        } else {
            $(share_from_err).hide();
        }

        if ( share_to == '' ) {
            errors = true;
            $(share_to_err).html(lang_share_rec_empty + '<br />');
            $(share_to_err).fadeIn();
        } else {
            $(share_to_err).hide();
        }

        if ( errors ) {
            return false;
        }

        var selector = '#share_' + type + '_response';
        $(selector).removeClass().addClass('posting');
        $(selector).html(lang_sending);
        $(selector).fadeIn();
        $.post(base_url + '/ajax/share_' + type, { item_id: item_id, from: share_from, to: share_to, message: share_message },
        function(response) {
            if ( response.status == 0 ) {
                user_posting('#share_' + type + '_response', response.msg);
            } else {
                user_response('#share_' + type + '_response', response.msg);
                setTimeout("destroy('#share_" + type + "_box')", 3500);
            }
        }, 'json');
    });
    
    $("input[id*='submit_flag_']").click(function(event) {
        event.preventDefault();
        var click_id        = $(this).attr('id');
        var id_split        = click_id.split('_');
        var type            = id_split[2];
        var item_id         = id_split[3];
        var flag_id         = $("input[name='flag_reason']:checked").val();
        var message         = $("textarea[id='flag_message']").val();
        
        user_posting('#flag_' + type + '_response', lang_flaging);
        $.post(base_url + '/ajax/flag_' + type, { item_id: item_id, flag_id: flag_id, message: message },
        function(response) {
            if ( response.status == 0 ) {
                user_posting('#flag_' + type + '_response', response.msg);
            } else {
                user_response('#flag_' + type + '_response', response.msg);
                setTimeout("destroy('#flag_" + type + "_box')", 3500);
            }
        }, 'json');
    });
	
    $("body").on('click', ".change-language", function(event) {    	
		event.preventDefault();
		$("input[id='language']").val($(this).attr('id'));
		$("#languageSelect").submit();
	});
	
    $("body").on('click', "button[id*='close_attach_mc_']", function(event) {
        $('#media_content').fadeOut(); 
    });
	
	$("#wrapper").css("padding-bottom", $(".footer-container").height() + "px");
});

$( window ).resize(function() {
	$("#wrapper").css("padding-bottom", $(".footer-container").height() + "px");
});
