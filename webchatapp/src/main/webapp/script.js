var msglistOut = [];
var msglistIn = [];
var msglist = [];
var counter = -1;
var myName;
var sendOrEdit = 0;
var replaceNode;
var msg_div;
var repl;

function run(){
    localStorage.clear();
    myName = document.getElementById('nickname').value;
    document.getElementById('badserver').style.display = "none";
    msglist = loadMessages();
   showMessages(msglist);
}

sendMsgBtn.onclick = function() {
    var messText = document.getElementById('txt');
    var text = messText.value;
    if (sendOrEdit == 0) {
        if (text != "") {
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


            counter++;
            var messIn = newMessage(msgIn, "Бочер", 'in', false, counter, false);
            counter++;
            var messOut = newMessage(msgOut, myName, 'out', false, counter, false);
            if (msglist == null) {
                msglist = [messOut];
            }
            else {
                msglist.push(messOut);
            }
            msglist.push(messIn);
            saveMessages(msglist);
            fixOnClick();
            fixEditOnClick();
        }
    }
    else{
        if(text != "") {
            var msgShow = document.createElement('remove-outcoming');
            var temp = newMessage(text, myName, "out", false, 0, true);
            msgShow.innerHTML = '<div><div class="message message-outcoming">' + temp.message + '</div> ' +
                ' <div class="control control-outcoming">' +
                    "Message was edited"+
                '</div></div>';
            msg_div.replaceChild(msgShow, replaceNode);
            sendOrEdit = 0;
            replaceElement(repl, msglist, temp);
            document.getElementById('txt').value = '';
            saveMessages(msglist);
            fixEditOnClick();
            fixOnClick();
        }
    }

};

changeName.onclick = function(){

    var newName = document.getElementById('nickname');
    myName = newName.value;
};

function delete_on_click() {
    console.debug(this.parentElement.parentElement);
    var msg_outer_div = this.parentElement.parentElement.parentElement;
    var text = "";
    var temp = newMessage("Message was deleted", myName, "out", true, 0, false);
    for (var i = msg_outer_div.children.length - 1; i >= 0; i--) {
        if (msg_outer_div.children[i].className == "message message-outcoming") {
            text = msg_outer_div.children[i].textContent;
            var msgShow = document.createElement('remove-outcoming');
            msgShow.innerHTML = '<div class="message remove-outcoming">Message was deleted</div>';
            msg_outer_div.replaceChild(msgShow, msg_outer_div.children[i]);
        }
    }
    replaceElement(text, msglist, temp);
    saveMessages(msglist);
    return false;
}
fixOnClick();

function edit_on_click(){
    console.debug(this.parentElement.parentElement);
    var msg_outer_div = this.parentElement.parentElement.parentElement;
    msg_div = msg_outer_div;
    var text = "";
    for (var i = msg_outer_div.children.length - 1; i >= 0; i--) {
        if (msg_outer_div.children[i].className == "message message-outcoming") {
            text = msg_outer_div.children[i].textContent;
            document.getElementById('txt').value = text;
            repl = text;
           replaceNode = msg_outer_div.children[i];
            sendOrEdit = 1;
        }
    }
    return false;
}
fixEditOnClick();

function newMessage(text, auth, send, del, counter, ed){
    return{
        message:text,
        author:auth,
        sender:send,
        remove:del,
        counter:counter,
        edit:ed
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


function fixOnClick(){
    var delete_divs = document.getElementsByClassName('delete-my-msg');
    for (var i = delete_divs.length - 1; i >= 0; i--) {
        delete_divs[i].children[0].onclick = delete_on_click;
    }
}

function fixEditOnClick(){
    var edit_divs = document.getElementsByClassName('edit-my-msg');
    for(var i = edit_divs.length - 1; i >= 0; i--) {
        edit_divs[i].children[0].onclick = edit_on_click;
    }
}

function scrollToEnd(){
    var block = document.getElementById("history");
    block.scrollTop = block.scrollHeight;
}

function showMessages(msglist){
    if(msglist != null) {
        myName = findLastName(msglist);
        document.getElementById('nickname').value = myName;
        for (var i = 0; i < msglist.length; i++) {
            if(msglist[i].remove == true && msglist[i].sender == "out"){
                var remMess = document.createElement('remove-outcoming');
                remMess.innerHTML = '<div class="message remove-outcoming">' + msglist[i].message + '</div>';
                document.getElementById('history').appendChild(remMess);
            }
            else if(msglist[i].edit == true && msglist[i].sender == "out"){
                var editMess = document.createElement('message-outcoming');
                editMess.innerHTML = '<div><div class="message message-outcoming">' + msglist[i].message + '</div> ' +
                    ' <div class="control control-outcoming">' +
                    "Message was edited"+
                    '<div class="delete-my-msg"><a href="#">Delete</a>/</div>' +
                    '<div class="edit-my-msg"><a href="#">Edit</a></div>' +
                    '</div></div>';
                document.getElementById('history').appendChild(editMess);
            }
            else {
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
                if (msglist[i].sender == "in") {
                    var inMess = document.createElement('message-incoming');
                    inMess.innerHTML = '<div class="message message-incoming">' + msglist[i].message + '</div>' +
                        '<div class="badge badge-incoming">Бочер</div><div>';
                    document.getElementById('history').appendChild(inMess);
                }
            }
        }

    }
    scrollToEnd();
}
