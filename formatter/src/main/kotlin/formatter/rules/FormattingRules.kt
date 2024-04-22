package formatter.rules

import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream

interface FormattingRules<T> {
    fun applyRule(): T

    fun getConfigFileValue(
        ruleName: String,
        convert: (String) -> T,
    ): T {
        val input = FileInputStream("formatter/src/main/resources/format_rules.yaml")
        val yaml = Yaml()
        val data = yaml.load(input) as Map<String, Map<String, Any>>
        val rulesMap = data["rules"] ?: throw IllegalArgumentException("Invalid YAML content")

        val keyValue =
            when (ruleName) {
                "spaceBeforeColon" -> rulesMap["spaceBeforeColon"]
                "spaceAfterColon" -> rulesMap["spaceAfterColon"].toString()
                "spaceAroundAssignment" -> rulesMap["spaceAroundAssignment"].toString()
                "newlineBeforePrintln" -> rulesMap["newlineBeforePrintln"].toString()
                else -> throw IllegalArgumentException("Invalid rule name")
            }
        return convert(keyValue.toString())
    }
}
