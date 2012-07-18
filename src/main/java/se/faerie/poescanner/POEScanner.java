package se.faerie.poescanner;


public class POEScanner implements Runnable {
	private long baseSleepTime = 2000L;
	private long randSleepTime = 2000L;

	private PostFinder postFinder = new PostFinder();
	private KeyFinder keyFinder = new KeyFinder();
	private UserInteraction userInteraction = new UserInteraction();
	private KeyRegister keyRegister;

	public POEScanner(String login, String password) {
		this.keyRegister = new KeyRegister(login, password);
	}

	public void run() {
		this.userInteraction.log("Starting scanner");
		while (true)
			try {
				for (String post : this.postFinder.getNewPosts()) {
					for (String key : this.keyFinder.findCdKeys(post)) {
						if (this.keyRegister.tryRegisterKey(key)) {
							this.userInteraction.alertNewCdKey(key);
						} else {
							this.userInteraction.log("Key " + key
									+ " appears invalid");
						}
					}
				}

				Thread.sleep((long) (this.baseSleepTime + Math.random()
						* this.randSleepTime));
			} catch (Exception e) {
				this.userInteraction.alertError(e.getMessage());
			}
	}

	public static final void main(String[] args) {
		if (args.length != 2) {
			System.err
					.println("Usage: java -jar POEScanner-1.0-executable.jar <login> <password>");
		}
		new Thread(new POEScanner(args[0], args[1]), "POEScanner").start();
	}
}