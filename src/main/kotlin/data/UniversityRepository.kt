package com.edumatch.data

import com.edumatch.models.University

class UniversityRepository {
    val universities = listOf(
        University(
            name = "Массачусетский Технологический Институт",
            country = "США",
            ranking = 1,
            annualCost = 55000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "OPT до 3 лет для STEM специальностей",
            applicationUrl = "https://mit.edu/admissions"
        ),
        University(
            name = "Стэнфордский Университет",
            country = "США",
            ranking = 2,
            annualCost = 56000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "OPT до 3 лет для STEM специальностей",
            applicationUrl = "https://stanford.edu/admissions"
        ),
        University(
            name = "Гарвардский Университет",
            country = "США",
            ranking = 3,
            annualCost = 54000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "OPT до 3 лет для STEM специальностей",
            applicationUrl = "https://college.harvard.edu/admissions"
        ),
        University(
            name = "Оксфордский Университет",
            country = "Великобритания",
            ranking = 4,
            annualCost = 45000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "Graduate Route виза на 2 года",
            applicationUrl = "https://ox.ac.uk/admissions"
        ),
        University(
            name = "Кембриджский Университет",
            country = "Великобритания",
            ranking = 5,
            annualCost = 44000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "Graduate Route виза на 2 года",
            applicationUrl = "https://www.cam.ac.uk/admissions"
        ),
        University(
            name = "Университет Торонто",
            country = "Канада",
            ranking = 18,
            annualCost = 40000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "PGWP до 3 лет после выпуска",
            applicationUrl = "https://future.utoronto.ca"
        ),
        University(
            name = "Университет Британской Колумбии",
            country = "Канада",
            ranking = 34,
            annualCost = 38000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "PGWP до 3 лет после выпуска",
            applicationUrl = "https://you.ubc.ca"
        ),
        University(
            name = "Университет Мельбурна",
            country = "Австралия",
            ranking = 33,
            annualCost = 38000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "Временная виза выпускника до 4 лет",
            applicationUrl = "https://study.unimelb.edu.au"
        ),
        University(
            name = "Австралийский Национальный Университет",
            country = "Австралия",
            ranking = 27,
            annualCost = 37000,
            admissionChance = 0.0,
            scholarshipChance = 0.0,
            workVisaPolicy = "Временная виза выпускника до 4 лет",
            applicationUrl = "https://www.anu.edu.au/study"
        )
    )

    fun getByCountry(country: String): List<University> {
        return universities.filter { it.country == country }
    }

    fun getByRankingRange(from: Int, to: Int): List<University> {
        return universities.filter { it.ranking in from..to }
    }

    fun getByMaxCost(maxCost: Int): List<University> {
        return universities.filter { it.annualCost <= maxCost }
    }
}