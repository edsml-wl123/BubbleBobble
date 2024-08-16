package com.example.bubblebobble.SoundEffect;

//import javax.sound.sampled.*;
import javafx.scene.media.*;
import java.net.URL;


/**
 * SoundEffect handles the game's sound effect.
 * Classes that want to use sound effect will call the static variables in this enum and
 * play them via the play() method.
 */
public enum SoundEffect {
	FRUIT("/sfx/fruit.wav"),
	DEATH("/sfx/death.wav"),
	SHOOT("/sfx/shoot.wav"),
	POP("/sfx/pop.wav"),
	BUBBLED("/sfx/bubbled.wav"),
	JUMP("/sfx/jump.wav"),
	EXPLODE("/sfx/explode.wav"),
	LAND("/sfx/land.wav"),
	FRUIT2("/sfx/fruit2.mp3"),
	WIN("/sfx/win.mp3");

	
	private AudioClip clip;

	/**
	 * Constructor of SoundEffect.
	 * @param soundFileName input the audio file name
	 */
	SoundEffect(String soundFileName) {
		URL url = this.getClass().getResource(soundFileName);
		if(url==null){
			throw new NullPointerException("Cannot find the sound file");
		}
		clip = new AudioClip(url.toExternalForm());
	}
	
	public void play() {
		if (clip.isPlaying()) {
			clip.stop();
		}
		clip.play();
	}

	/**
	 * Set volume of the clip to 0.5.
	 */
	public void setToLoud() {
		// sets volume to be loud
		clip.setVolume(0.5);
	}

	/**
	 * Set volume of the clip to 0.8.
	 * This is used on static variables in the enum which play MP3 files,
	 * because these MP3 files are generally more low in volume.
	 */
	public void setToMoreLoud(){
		clip.setVolume(0.8);
	}
}
