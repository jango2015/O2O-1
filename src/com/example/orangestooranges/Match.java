package com.example.orangestooranges;

import java.util.ArrayList;
import java.util.Stack;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {
	//attributes
	ArrayList<Player> players; //array of players
	private int numPlayers; //number of players
	int round; //current round
	int maxScore; //max score needed to win
	boolean isOver; //false until, for whatever reason, game is over
	int isJudge; //index of judge
	ArrayList<CardOrange> inPlay; //arrayList of oranges in play for round
	int match_ID; //ID unique to this match
	CardBlue roundBlue;
	int winnerIndex;
	Stack<CardBlue> blueStack = new Stack<CardBlue>();
	Stack<CardOrange> orangeStack = new Stack<CardOrange>();
	int gameStart = 0; //Used for name filling in 0 false 1 true

	//constructors
	Match() {
		players = new ArrayList<Player>();
	}
	
	Match(int numPlayers, int maxScore, int match_ID, Stack<CardBlue> blueStack, Stack<CardOrange> orangeStack) {
		this.numPlayers = numPlayers;
		this.maxScore = maxScore;
		this.match_ID = match_ID;
		round = 0;
		isOver = false;
		isJudge = 0;
		roundBlue = new CardBlue();
		players = new ArrayList<Player>();
		for(int i = 0; i < numPlayers; i++)
			players.add(new Player());
		inPlay = new ArrayList<CardOrange>();
		for(int i = 0; i < numPlayers; i++) {
			inPlay.add(null);
		}
		this.blueStack = blueStack;
		this.orangeStack = orangeStack;
	}
	
	//match from parcel
	Match(Parcel in) {
		this();
		in.readList(players, getClass().getClassLoader()); 
		numPlayers = in.readInt();
		round = in.readInt();
		maxScore = in.readInt();
		isOver = in.readByte() != 0;  //isOver == true if byte != 0
		isJudge = in.readInt();
		inPlay = new ArrayList<CardOrange>();
		in.readList(inPlay, getClass().getClassLoader());
		match_ID = in.readInt();
		roundBlue = in.readParcelable(CardBlue.class.getClassLoader());
		winnerIndex = in.readInt();
		in.readList(blueStack, getClass().getClassLoader());
		in.readList(orangeStack, getClass().getClassLoader());
		gameStart = in.readInt();
	}
	
	//method invoked by parcelable
	public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
		public Match createFromParcel(Parcel in) {
			return new Match(in);
		}
		
		public Match[] newArray(int size) {
			return new Match[size];
		}
	};
	
	public void dealOranges(Player p)
	{
		while(p.getHand().size() < 7)
		{
			p.addOrange(orangeStack.pop());
		}
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public void setInPlay(CardOrange yourOrange, int playerIndex) {
		inPlay.set(playerIndex, yourOrange);
	}
	
	public void setRoundBlue(CardBlue roundBlue) {
		this.roundBlue = roundBlue;
	}
	
	public void addPlayer(Player player) {
		if(players.size() < numPlayers) 
			players.add(player);
	}
	
	public CardBlue getRoundBlue() {
		return this.roundBlue;
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	/* will set the winner of the round; will reset to -1 at end of each round, and if judge doesn't select
	 * a winner, it will award points to every player that did not random
	 */
	
	public void handWinner(CardBlue blueWon) {
		if(winnerIndex != -1) {
			players.get(winnerIndex).updatePoints(blueWon);
		} else {
			for(int i = 0; i < numPlayers; i++) {
				players.get(i).updatePoints(blueWon);
			}
		}
	}
	
	//makes current judge a player, and 'judges' next judge
	public void makeJudge() {
		players.get(isJudge).unmakeJudge();
		if(isJudge == (numPlayers-1)) {
			isJudge = 0;
		} else
			isJudge++;
		players.get(isJudge).makeJudge();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(players);
		dest.writeInt(numPlayers);
		dest.writeInt(round);
		dest.writeInt(maxScore);
		dest.writeByte((byte) (isOver ? 1 : 0)); //true = 1
		dest.writeInt(isJudge);
		dest.writeList(inPlay);
		dest.writeInt(match_ID); 
		dest.writeParcelable(roundBlue, flags);
		dest.writeInt(winnerIndex);
		dest.writeList(blueStack);
		dest.writeList(orangeStack);
	}
	
	public void resetRound() {
		for(int i = 0; i < getNumPlayers(); i++) {
			getPlayer(i).resetRound();
			inPlay.set(i, null);
			//more functionality will get added here... not sure what for now
		}
	}
		
}
