function do_game() {
    var colors = ["blue", "cyan", "gold", "green", "magenta", "orange", "red", "white", "yellow"];
    colors.sort();
    var target_index = Math.floor(Math.random() * colors.length);
    var target = colors[target_index];
    var finished = false;
    var guess_input;
    var guesses = 0;

    while (!finished) {
        guess_input = prompt("I am thinking of one of these colors: \n\n"+
                                    colors.join(", ")+"\n\n"+
                                    "What color am I thinking of?");
        var guess_index = colors.indexOf(guess_input);
        guesses += 1;
        finished = check_guess(guess_index, target_index, guesses, guess_input);
    }
}

function check_guess(guess_index, target_index, guesses, color_name) {
    if (guess_index == -1) {
        alert("Sorry, I don't recognize your color.\n\n"+
                "Please try again.");
        return false;
    } else if (guess_index > target_index){
        alert("Sorry, your guess is not correct!\n\n"+
                "Hint: your color is alphabetically higher than mine.\n\n"+
                "Please try again.");
        return false;
    } else if (guess_index < target_index){
        alert("Sorry, your guess is not correct!\n\n"+
                "Hint: your color is alphabetically lower than mine.\n\n"+
                "Please try again.");
        return false;
    } else {
        document.getElementsByTagName("body")[0].style.background = color_name;
        alert("Congratulations! You have guessed the color!\n\n"+
                "It took you "+guesses+" guesses to finish the game!\n\n"+
                "You can see the color in the background.");
        return true;
    }
}
