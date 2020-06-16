package hr.fer.zemris.java.hw14;

public class PollEntry implements Comparable<PollEntry>{

	private long id;
	private String title;
	private String link;
	private long pollId;
	private long votesCount;
	
	public PollEntry() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getPollId() {
		return pollId;
	}

	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	public long getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public int compareTo(PollEntry o) {
		return Long.valueOf(o.getVotesCount()).compareTo(votesCount);
	}
	
}
