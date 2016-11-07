package com.quickshear.common.wechat.domain;

/**
 * 音乐消息
 * 
 * @author liuyh
 *
 */
public class MusicMessage extends BaseMessage {
  // 音乐
  private Music Music;

  public Music getMusic() {
    return Music;
  }

  public void setMusic(Music music) {
    Music = music;
  }
}
