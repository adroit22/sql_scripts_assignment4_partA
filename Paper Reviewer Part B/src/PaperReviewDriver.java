
import java.sql.*;

public class PaperReviewDriver {

	private static final String HOST = "jdbc:mysql://localhost/";
	private static final String DB = "conference_review";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";
	private static final String QUERY_1 = "select p.Id as PaperId, p.Title, p.Abstract, a.EmailAddr as EmailAddress, a.FirstName, a.LastName from paper as p join author as a on a.EmailAddr= p.AuthorId where p.AuthorId= ? ";
	private static final String QUERY_2 = "select r.* from review as r where r.Recommendation = 'PUBLISH' and r.PaperId= ?";
	private static final String QUERY_3 = "select count(Id) as count from paper";
	private static final String QUERY_4_1 = "insert into AUTHOR(EmailAddr, FirstName, LastName) Values(?, ?, ?)";
	/* 'petyr@gmail.com', 'Petyr', 'Baelish' */
	private static final String QUERY_4_2 = "insert INTO PAPER(Id, Title, Abstract , FileName, AuthorId) VALUES (?, ?, ?, ?, ?)";
	/*
	 * 105, 'The Dragon and the Wolf', 'Based on story of Master of Coin',
	 * 'Littlefinger', 'petyr@gmail.com'
	 */
	private static final String QUERY_5 = "delete from author where EmailAddr = ?";

	public static void main(String[] args) {

		try (Connection con = DriverManager.getConnection(HOST + DB, USER, PASSWORD)) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			/* Query 1 execution */
			System.out.println("\n==============QUERY 1 - Execution START==============\n");
			System.out.println("Get submitted paper details for AuthorId- Daenerys@got.com from database- \n");
			getSubmittedPaperDetails("Daenerys@got.com", con);
			System.out.println("\n");

			/* Query 2 execution */
			System.out.println("\n==============QUERY 2 - Execution START==============\n");
			System.out.println("Get all reviews for PaprId- 103  from database- \n");
			getReviewDetails(103, con);
			System.out.println("\n");

			/* Query 3 execution */
			System.out.println("\n==============QUERY 3 - Execution START==============\n");
			System.out.println("Counting All Papers submitted \n");
			getAllPapersSubmitted(con);
			System.out.println("\n");

			/* Query 4 execution */
			System.out.println("\n==============QUERY 4 - Execution START==============\n");
			System.out.println("Creating a new Paper Submission, adding entries to both Author and Paper tables- \n");
			createNewPaperSubmission("test@gmail.com", "Jon", "Doe", 110, "The Dragon and the Wolf",
					"Based on story of Master of Coin", "File_one", con);
			System.out.println("\n");

			/* Query 5 execution */
			System.out.println("\n==============QUERY 5 - Execution START==============\n");
			System.out.println("Delete first row in Author table with id - Daenerys@got.com from database- \n");
			deleteAuthor("Daenerys@got.com", con);
			System.out.println("\n");

		} catch (Exception e) {
			System.out.println("Exception while executing queries!");
			e.printStackTrace();
		}

	}
	
	private static void getSubmittedPaperDetails(String authorId, Connection con) throws SQLException {
		PreparedStatement preparedStatement = con.prepareStatement(QUERY_1);
		preparedStatement.setString(1, authorId);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			rs.beforeFirst();
			while (rs.next()) {
				String email = rs.getString("EmailAddress");
				String paperId = rs.getString("PaperId");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String title = rs.getString("Title");
				String details = rs.getString("Abstract");
				System.out.printf("AuthorId- %s\tPaperId- %s\tFirst Name- %s\tLast Name- %s\tTitle- %s\tAbstract- %s\n",
						email, paperId, firstName, lastName, title, details);
			}
		} else {
			System.out.println("No details found for AuthorId - " + authorId + ", check it and try again!");
		}
	}
	
	private static void getReviewDetails(int paperId, Connection con) throws SQLException {
		PreparedStatement preparedStatement = con.prepareStatement(QUERY_2);
		preparedStatement.setInt(1, paperId);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			rs.beforeFirst();
			while (rs.next()) {
				int paperIddb = rs.getInt("PaperId");
				int reviewId = rs.getInt("Id");
				int meritScore = rs.getInt("MeritScore");
				int readabilityScore = rs.getInt("ReadabilityScore");
				int releveanceScore = rs.getInt("ReleveanceScore");
				int originalityScore = rs.getInt("OriginalityScore");
				String recommendation = rs.getString("Recommendation");
				String reviewerId = rs.getString("ReviewerId");
				System.out.printf(
						"PaperId- %d\tReviewId- %d\tMeritScore- %d\tReadabilityScore- %d\tReleveanceScore- %d\tOriginalityScore- %d\tRecommendation- %s\tReviewerId- %s\n",
						paperIddb, reviewId, meritScore, readabilityScore, releveanceScore, originalityScore,
						recommendation, reviewerId);
			}
		} else {
			System.out.println("No details found for PaperId- " + paperId + ", check it and try again!");
		}

	}
	
	private static void getAllPapersSubmitted(Connection con) throws SQLException {
		PreparedStatement preparedStatement = con.prepareStatement(QUERY_3);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int count = rs.getInt("count");
			System.out.println("Total papers submitted = " + count);
		}
	}

	private static void createNewPaperSubmission(String EmailAddr, String FirstName, String LastName, int id,
			String Title, String Abstract, String FileName, Connection con) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement(QUERY_4_1);
			preparedStatement.setString(1, EmailAddr);
			preparedStatement.setString(2, FirstName);
			preparedStatement.setString(3, LastName);
			int status = preparedStatement.executeUpdate();
			System.out.println("New AUTHOR added- Id: " + EmailAddr + " First Name: " + FirstName + " Last Name: " + LastName);
			System.out.println("\n");
			System.out.println("Adding paper details in PAPER table . . .");
		} catch (SQLException e) {
			System.out.println(
					"EXCEPTION while inserting in AUTHOR table! Author id " + EmailAddr + " couldnot be inserted.");
			System.out.println("Confirm that authorId- " + EmailAddr + " doesnot already exists in the table");
		}
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(QUERY_4_2);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, Title);
			preparedStatement.setString(3, Abstract);
			preparedStatement.setString(4, FileName);
			preparedStatement.setString(5, EmailAddr);
			int status = preparedStatement.executeUpdate();
			System.out.println("Paper added.");
			System.out.println("PaperId: " + id + " Title: " + Title + " Abstract: " + Abstract + "FileName: " + FileName + " AuthorId: " + EmailAddr);
		} catch (SQLException e) {
			System.out.println(
					"EXCEPTION while inserting in PAPER table!");
			System.out.println("Confirm that paperId- " + id + " doesnot already exists in the table");
		}

	}

	private static void deleteAuthor(String authorId, Connection con) {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(QUERY_5);
			preparedStatement.setString(1, authorId);
			int status = preparedStatement.executeUpdate();
			System.out.println("Author entry for id = " + authorId + " deleted successfully");
		} catch (SQLException e) {
			System.out
					.println("EXCEPTION while deleting authorId - " + authorId + ". ForeignKey Constraint violation!");
			System.out.println("AuthorId is a foreign key in other tables, first remove references to " + authorId
					+ " in them and then proceed for deletion. Details of error are -");
			e.printStackTrace();
		}

	}

}
