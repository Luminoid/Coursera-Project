var faceNum = 5;

function generateFaces() {
    var theBody = document.getElementsByTagName("body")[0];
    var theLeftSide = document.getElementById("leftSide");
    var theRightSide = document.getElementById("rightSide");

    // Left Side Face Images
    for (var i = 0; i < faceNum; i++) {
        var imgTopPos = Math.floor(Math.random() * 400);
        var imgLeftPos = Math.floor(Math.random() * 400);
        var faceImg = document.createElement("img");

        faceImg.src = "img/smile.png";
        faceImg.style.top = imgTopPos + "px";
        faceImg.style.left = imgLeftPos + "px";
        faceImg.onclick =
            function gameOver() {
                alert("Game Over")
                location.reload();
            }
        theLeftSide.appendChild(faceImg);
    }

    // Right Side Face Images
    var leftSideImages = theLeftSide.cloneNode(true);
    leftSideImages.removeChild(leftSideImages.lastChild);
    theRightSide.appendChild(leftSideImages);

    // The distinct node
    theLeftSide.lastChild.onclick = null;
    theLeftSide.lastChild.onclick =
        function nextLevel(event) {
            event.stopPropagation();
            while (theLeftSide.firstChild) {
                theLeftSide.removeChild(theLeftSide.firstChild)
            }
            while (theRightSide.firstChild) {
                theRightSide.removeChild(theRightSide.firstChild)
            }
            faceNum += 5;
            generateFaces();
        }
}
