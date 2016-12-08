## Solution template for Guess The Word practice problem (section 7)

require_relative './section-7-provided'

class ExtendedGuessTheWordGame < GuessTheWordGame

end

class ExtendedSecretWord < SecretWord
  def initialize word
    super word
    found = self.word.index(/[^a-zA-Z]/)
    if found
      start = 0
      while ix = self.word.index(/[^a-zA-Z]/, start)
        @pattern[ix] = @word[ix]
        start = ix + 1
      end
    end
    @guess_list = []
  end

  def valid_guess? guess
    isValid = (guess =~ /^[a-zA-Z]{1}$/ && !(@guess_list.include? guess.downcase))
    @guess_list.push guess.downcase
    isValid
  end

  def guess_letter! letter
    found = self.word.index(/#{letter}/i)
    if found
      start = 0
      while ix = self.word.index(/#{letter}/i, start)
        self.pattern[ix] = self.word[ix]
        start = ix + 1
      end
    end
    found
  end
end

## Change to `false` to run the original game
if true
  ExtendedGuessTheWordGame.new(ExtendedSecretWord).play
else
  GuessTheWordGame.new(SecretWord).play
end