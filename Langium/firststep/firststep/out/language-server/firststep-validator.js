"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.FirststepValidator = exports.FirststepValidationRegistry = void 0;
const langium_1 = require("langium");
/**
 * Registry for validation checks.
 */
class FirststepValidationRegistry extends langium_1.ValidationRegistry {
    constructor(services) {
        super(services);
        const validator = services.validation.FirststepValidator;
        const checks = {
            Person: validator.checkPersonStartsWithCapital
        };
        this.register(checks, validator);
    }
}
exports.FirststepValidationRegistry = FirststepValidationRegistry;
/**
 * Implementation of custom validations.
 */
class FirststepValidator {
    checkPersonStartsWithCapital(person, accept) {
        if (person.name) {
            const firstChar = person.name.substring(0, 1);
            if (firstChar.toUpperCase() !== firstChar) {
                accept('warning', 'Person name should start with a capital.', { node: person, property: 'name' });
            }
        }
    }
}
exports.FirststepValidator = FirststepValidator;
//# sourceMappingURL=firststep-validator.js.map