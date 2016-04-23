var msglistOut = [];
var msglistIn = [];
var msglist = [];
var counter = -1;

function run(){
    var my_name = $('#nickname').val();
    $('#badserver').hide();
    msglist = loadMessages();
    if(msglist != null) {
        for (var i = 0; i < msglist.length; i++) {
            var outer = $('<div></div>');
            if(msglist[i].remove == true){
                outer.append('<div class="message remove-outcoming">' + msglist[i].message + '</div>');
                $('#history').append(outer);
            }
            else {
                if (msglist[i].sender == 'out') {
                    outer.append('<div class="message message-outcoming">' + msglist[i].message + '</div>');
                    outer.append('<div class="badge badge-outcoming">' + msglist[i].author + '</div>');
                    outer.append('<div class="control control-outcoming">\
                          <a href="#" class="delete-my-msg">Delete</a>\
                          <a href="#" class="edit-my-msg">Edit</a>\
                      </div>');
                    $('#history').append(outer);
                }
                else {
                    $('#history').append('<div class="message message-incoming">' + msglist[i].message + '</div>');
                    $('#history').append('<div class="badge badge-incoming">Бочер</div>');
                }
            }
            // $('#history').animate({scrollTop: $('body').height()}, 200);
        }
    }

}

function sendMessage(){
    var messText = document.getElementById('sendMsgBtn');
    var text = messText.value;
    if(text != ""){
        var msgOut = text;
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

        counter ++;
        var messIn = newMessage(msgIn, "Бочер",'in', false, counter);
        counter ++;
        var messOut = newMessage(msgOut, my_name, 'out', false, counter);
        if(msglist == null){
            msglist = [messOut];
        }
        else {
            msglist.push(messOut);
        }
        msglist.push(messIn);

        msglistIn.push(messIn);
        msglistOut.push(messOut);
        saveMessages(msglist);

    }

}


function newMessage(text, auth, send, del, counter){
    return{
        message:text,
        author:auth,
        sender:send,
        remove:del,
        counter:counter
    };
}

function saveMessages(listToSave){
    if(typeof(Storage) == "undefined"){
        alert('localStorage is not accessible');
        return;
    }
    localStorage.setItem("Messages", JSON.stringify(listToSave));
}

function loadMessages(){
    if(typeof(Storage) == "undefined"){
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("Messages");
    return item && JSON.parse(item);
}

function replaceElement(element, list, new_msg){
    for(var i = 0; i < list.length; ++i){
        if(list[i].message == element){
            list[i] = new_msg;
        }
    }
}