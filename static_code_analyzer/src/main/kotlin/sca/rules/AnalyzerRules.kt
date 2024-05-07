package sca.rules

import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream

interface AnalyzerRules {
    fun applyRule(): Boolean

    fun getConfigFileValue(
        ruleName: String,
        convert: (String) -> Boolean,
    ): Boolean {
        val input = FileInputStream("/Users/maiacamarero/IdeaProjects/print-script/static_code_analyzer/src/main/resources/sca_rules.yaml")
        val yaml = Yaml()
        val data = yaml.load(input) as Map<String, Map<String, Any>>
        val rulesMap = data["rules"] ?: throw IllegalArgumentException("Invalid YAML content")

        val keyValue =
            when (ruleName) {
                "printlnArgumentCheck" -> rulesMap["printlnArgumentCheck"] // quizas esta sirve para lo de readInput
                "typeMatchingCheck" -> rulesMap["typeMatchingCheck"].toString()
                "identifierNamingCheck" -> rulesMap["identifierNamingCheck"].toString()
                else -> throw IllegalArgumentException("Invalid rule name")
            }
        return convert(keyValue.toString())
    }
}
