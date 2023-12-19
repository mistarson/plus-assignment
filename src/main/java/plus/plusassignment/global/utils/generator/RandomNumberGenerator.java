package plus.plusassignment.global.utils.generator;

import org.springframework.stereotype.Service;

@Service
public class RandomNumberGenerator implements NumberGenerator {

    @Override
    public String numberGenerate() {
        return String.valueOf((int) (Math.random() * 899999) + 100000);
    }
}
