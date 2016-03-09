$(document).ready(function() {
    var msglistOut = [];
    var msglistIn = [];
    var my_name = $('#nickname').val();

    $('#badserver').hide();

    $('#sendMsgBtn').click(function() {
        var msgOut = $('#txt').val();
        $('#txt').val('');
        var outer = $('<div></div>');
        outer.append('<div class="message message-outcoming">' + msgOut  +'</div>');
        my_name = $('#nickname').val();
        outer.append('<div class="badge badge-outcoming">' + my_name + '</div>');
        outer.append('<div class="control control-outcoming">\
                          <a href="#" class="delete-my-msg">Delete</a>\
                          <a href="#" class="edit-my-msg">Edit</a>\
                      </div>');
        $('#history').append(outer);

        var msgIn = msgOut.split("").reverse().join("");
        $('#history').append('<div class="message message-incoming">' + msgIn + '</div>');
        $('#history').append('<div class="badge badge-incoming">Бочер</div>');
        $('#history').animate({scrollTop: $('body').height() }, 200);

        msglistIn.push(msgIn);
        msglistOut.push(msgOut);

        $('.delete-my-msg').click(function() {
            $(this).parent().parent().html('<div class="message remove-outcoming">Message was deleted</div>');
            return false;
        });
        $('.edit-my-msg').click(function(){
            var new_msg = prompt('msgOut');
            $(this).parent().parent().children('.message').html(new_msg);
            $('<div class="control control-outcoming">Message was edited</div>').insertAfter($(this).parent().parent().children('.message'));
            return false;
        })
    });

    $('.delete-my-msg').click(function() {
        msglistOut.splice(msglistOut.indexOf('$(this).parent().parent()'), 1);
        $(this).parent().parent().html('<div class="message remove-outcoming">Message was deleted</div>');
        return false;
    });

    $('#changeName').click(function(){
        my_name = $('#txt').val();
    });
    $('.edit-my-msg').click(function(){
        var new_msg = prompt('msgOut');
        $(this).parent().parent().children('.message').html(new_msg);
        $('<div class="control control-outcoming">Message was edited</div>').insertAfter($(this).parent().parent().children('.message'));
        return false;
    })
});