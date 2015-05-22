package com.o9pathshala.global;

import java.sql.Timestamp;
import java.util.List;

import com.o9pathshala.discussionfourm.dto.QuestionDTO;
import com.o9pathshala.discussionfourm.dto.TagDTO;
import com.o9pathshala.student.test.dto.TestDTO;
import com.o9pathshala.student.test.tabs.AllTestsAdapter;

public class GlobalData {

	public static List<QuestionDTO> questions;
	public static List<QuestionDTO> myQuestions;
	public static List<TestDTO> allTests;
	public static List<TestDTO> myTests;
	public static TestDTO testDTO;
	public static List<TagDTO> tags;
	public static Timestamp last_sync;
	public static AllTestsAdapter allTestAdapter;
	public static AllTestsAdapter attemptedestAdapter;
}
