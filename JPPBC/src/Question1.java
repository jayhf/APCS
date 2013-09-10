/**
 * The first problem of the year! Averaging grades to calculate a GPA.
 * 
 * @author Jay Fleischer
 * @version 1.0 (9-4-13)
 */
public class Question1 {
	/**
	 * Calculates a student's GPA given his/her grades.
	 * 
	 * @param grades
	 *            - all of the grades received, separated with a single space
	 * @return the calculated GPA
	 */
	public static double calculateGPA(String grades) {
		double gradeSum = 0;
		String[] gradeArray = grades.split(" ");
		for (String grade : gradeArray) {
			if (grade.startsWith("A"))
				gradeSum += 4;
			else if (grade.startsWith("B"))
				gradeSum += 3;
			else if (grade.startsWith("C"))
				gradeSum += 2;
			else if (grade.startsWith("D"))
				gradeSum += 1;
			if (grade.endsWith("+") && !grade.startsWith("A"))
				gradeSum += .3;
			else if (grade.endsWith("-"))
				gradeSum -= .3;
		}
		return gradeSum / gradeArray.length;
	}
}