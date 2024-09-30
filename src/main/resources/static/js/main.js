window.onload = function() {

    xhr.open("POST", "check", false);
    xhr.onreadystatechange = getAuthName;
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
};

const xhr = new XMLHttpRequest();

function getAuthName() {
    if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            const jsonObj = JSON.parse(xhr.response)
            if(!xhr.response.startsWith("<!DOCTYPE html>", 0) && jsonObj.result_code !== 'ERROR') { // 로그인 성공
                document.querySelector("#login-id").append(jsonObj.data + "님");
                document.querySelector("#login-succeed").style.display = "flex";
                document.querySelector(".main-menu").style.display = "flex";
                let sections = document.getElementsByTagName("section");
                for(let i=0; i<sections.length; i++) {
                    sections[i].style.display = "none";
                }

                // 로그인 성공으로 메인메뉴
                xhr.open("GET", "api/menu/1", false);
                xhr.onreadystatechange = getMainMenu;
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send();
            } else {
                document.querySelector(".main-menu").style.display = "flex";
                document.querySelector(".sub-menu").style.display = "flex";
                document.querySelector("#login-needed").style.display = "flex";
            }
        }
    }
}

function getMainMenu() {
    if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            const jsonObj = JSON.parse(xhr.response)

            let mainMenuArray = jsonObj.data.children;

            for(let i in mainMenuArray) {
                let liTag = document.createElement("li");
                let aTag = document.createElement("a");
                aTag.textContent = mainMenuArray[i].name;
                aTag.href = mainMenuArray[i].url;
                liTag.appendChild(aTag);
                document.querySelector(".main-menu").appendChild(liTag);
            }
        }
    }
}