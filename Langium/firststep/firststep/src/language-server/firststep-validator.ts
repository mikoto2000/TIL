import { ValidationAcceptor, ValidationCheck, ValidationRegistry } from 'langium';
import { FirststepAstType, Person } from './generated/ast';
import type { FirststepServices } from './firststep-module';

/**
 * Map AST node types to validation checks.
 */
type FirststepChecks = { [type in FirststepAstType]?: ValidationCheck | ValidationCheck[] }

/**
 * Registry for validation checks.
 */
export class FirststepValidationRegistry extends ValidationRegistry {
    constructor(services: FirststepServices) {
        super(services);
        const validator = services.validation.FirststepValidator;
        const checks: FirststepChecks = {
            Person: validator.checkPersonStartsWithCapital
        };
        this.register(checks, validator);
    }
}

/**
 * Implementation of custom validations.
 */
export class FirststepValidator {

    checkPersonStartsWithCapital(person: Person, accept: ValidationAcceptor): void {
        if (person.name) {
            const firstChar = person.name.substring(0, 1);
            if (firstChar.toUpperCase() !== firstChar) {
                accept('warning', 'Person name should start with a capital.', { node: person, property: 'name' });
            }
        }
    }

}
