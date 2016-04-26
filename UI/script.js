var msglistOut = [];
var msglistIn = [];
var msglist = [];
var counter = -1;
var myName;

function run(){
    //localStorage.clear();
    myName = document.getElementById('nickname').value;
    document.getElementById('badserver').style.display = "none";
    msglist = loadMessages();
    if(msglist != null) {
        myName = findLastName(msglist);
        document.getElementById('nickname').value = myName;
        for (var i = 0; i < msglist.length; i++) {
           /* if(msglist[i].remove == true){
                var remMess = document.createElement('remove-outcoming');
                remMess.innerHTML = '<div class="message remove-outcoming">' + msglist[i].message + '</div>';
                document.getElementById('history').appendChild(remMess);
            }*/
            if (msglist[i].sender == "out") {
                    var outMess = document.createElement('message-outcoming');
                    outMess.innerHTML = '<div><div class="message message-outcoming">' + msglist[i].message + '</div> ' +
                        '<div class="badge badge-outcoming">' + msglist[i].author + '</div>' +
                        ' <div class="control control-outcoming">' +
                        '<div class="delete-my-msg"><a href="#">Delete</a>/</div>' +
                        '<div class="edit-my-msg"><a href="#">Edit</a></div>' +
                        '</div></div>';
                    document.getElementById('history').appendChild(outMess);
                }
            if(msglist[i].sender == "in"){
                    var inMess = document.createElement('message-incoming');
                    inMess.innerHTML = '<div class="message message-incoming">' + msglist[i].message + '</div>' +
                        '<div class="badge badge-incoming">Бочер</div><div>';
                    document.getElementById('history').appendChild(inMess);
                }
            }
        scrollToEnd();
        }
}

sendMsgBtn.onclick = function(){
    var messText = document.getElementById('txt');
    var text = messText.value;
    if(text != ""){
        var msgOut = text;
        var sendMessage = document.createElement('message-outcoming');
        sendMessage.innerHTML = '<div><div class="message message-outcoming">' + msgOut + '</div> ' +
            '<div class="badge badge-outcoming">' + myName + '</div>' +
           ' <div class="control control-outcoming">' +
            '<div class="delete-my-msg"><a href="#">Delete</a>/</div>' +
            '<div class="edit-my-msg"><a href="#">Edit</a></div>' +
            '</div></div>';
        document.getElementById('history').appendChild(sendMessage);
        document.getElementById('txt').value = '';

        var msgIn = msgOut.split("").reverse().join("");
        var receiveMessage = document.createElement('message-incoming');
        receiveMessage.innerHTML = '<div class="message message-incoming">' + msgIn + '</div>' +
        '<div class="badge badge-incoming">Бочер</div><div>';
        document.getElementById('history').appendChild(receiveMessage);
        scrollToEnd();

        counter ++;
        var messIn = newMessage(msgIn, "Бочер",'in', false, counter);
        counter ++;
        var messOut = newMessage(msgOut, myName, 'out', false, counter);
        if(msglist == null){
            msglist = [messOut];
        }
        else {
            msglist.push(messOut);
        }
        msglist.push(messIn);
        saveMessages(msglist);
    }
};

changeName.onclick = function(){
    var newName = document.getElementById('nickname');
    myName = newName.value;
};

document.getElementsByClassName('.delete-my-msg').click = function(){
    alert("hey");
  //  msglistOut.splice(msglistOut.indexOf('$(this).parent().parent()'), 1);
            var text = this.parent().parent().children('.message').text();
           var temp = newMessage("Message was deleted", myName, "out", true, 0);
           replaceElement(text, msglist, temp);
            saveMessages(msglist);
    this.parent().parent().html('<div class="message remove-outcoming">Message was deleted</div>');
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

function findLastName(msglist){
    for(var i = msglist.length - 1; i >= 0; --i){
        if(msglist[i].sender == "out"){
            return msglist[i].author;
        }
    }
}

function replaceElement(element, list, new_msg){
    for(var i = 0; i < list.length; ++i){
        if(list[i].message == element){
            list[i] = new_msg;
        }
    }
}

function scrollToEnd(){
    var block = document.getElementById("history");
    block.scrollTop = block.scrollHeight;
}
