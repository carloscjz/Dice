package com.carranza.dice;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends Activity {

    ImageView dice_picture, dice_number;
    Random rWinner = new Random();
    SoundPool dice_sounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

    // used to control sound stream return by SoundPool
    int random_number;
    int sound_shake, sound_dice, number_dice;
    int sound1, sound2, sound3, sound4, sound5, sound6;

    boolean rolling = false;
    private Animation moveAnimation;
    private Animation bounceAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load dice sound
        sound_shake = dice_sounds.load(this, R.raw.shake, 1);
        sound1 = dice_sounds.load(this, R.raw.one, 1);
        sound2 = dice_sounds.load(this, R.raw.two, 1);
        sound3 = dice_sounds.load(this, R.raw.three, 1);
        sound4 = dice_sounds.load(this, R.raw.four, 1);
        sound5 = dice_sounds.load(this, R.raw.five, 1);
        sound6 = dice_sounds.load(this, R.raw.six, 1);

        // get reference to image widget
        dice_picture = (ImageView) findViewById(R.id.dice_picture);
        dice_number = (ImageView) findViewById(R.id.dice_number);

        // load dice move animation
        moveAnimation = AnimationUtils.loadAnimation(this, R.anim.move);
        moveAnimation.setRepeatCount(3);

        moveAnimation.setAnimationListener(animationListener);

        // load bounce animation
        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
    }

    public void HandleClick(View arg0) {

        if (!rolling) {
            rolling = true;

            // disable animation
            dice_picture.startAnimation(moveAnimation);
            dice_picture.setImageResource(R.drawable.d1);

            // start rolling sound
            dice_sounds.play(sound_shake, 1.0f, 1.0f, 0, 0, 1.0f);

            random_number = rWinner.nextInt(6) + 1;

            switch (random_number) {
                case 1:
                    dice_picture.setImageResource(R.drawable.d1);
                    number_dice = R.drawable.n1;

                    sound_dice = sound1;
                    break;
                case 2:
                    dice_picture.setImageResource(R.drawable.d2);
                    number_dice = R.drawable.n2;

                    sound_dice = sound2;
                    break;
                case 3:
                    dice_picture.setImageResource(R.drawable.d3);
                    number_dice = R.drawable.n3;

                    sound_dice = sound3;
                    break;
                case 4:
                    dice_picture.setImageResource(R.drawable.d4);
                    number_dice = R.drawable.n4;

                    sound_dice = sound4;
                    break;
                case 5:
                    dice_picture.setImageResource(R.drawable.d5);
                    number_dice = R.drawable.n5;

                    sound_dice = sound5;
                    break;
                case 6:
                    dice_picture.setImageResource(R.drawable.d6);
                    number_dice = R.drawable.n6;

                    sound_dice = sound6;
                    break;
                default:
            }

            rolling = false; // user can press again
        }
    }

    private AnimationListener animationListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            dice_number.setVisibility(View.GONE);

            // clear animation on start animation so the number back to original state
            dice_number.clearAnimation();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dice_picture.clearAnimation();

            // play dice sounds
            dice_sounds.play(sound_dice, 1.0f, 1.0f, 0, 0, 1.0f);

            // display dice number and start animation
            dice_number.setImageResource(number_dice);
            dice_number.startAnimation(bounceAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // do nothing
        }
    };
}
