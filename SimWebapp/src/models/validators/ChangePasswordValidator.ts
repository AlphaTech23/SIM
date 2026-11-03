import type { ValidationRule } from '@/models/validators/ValidationRule.ts'
import { validators } from '@/utils/validators.ts'

export class ChangePasswordValidator {
  static oldPasswordRule: ValidationRule[] = [
    {
      message: 'The current password is required',
      validate: (value: string) => validators.required(value),
    },
  ]

  static newPasswordRule: ValidationRule[] = [
    {
      message: 'The new password is required',
      validate: (value: string) => validators.required(value),
    },
  ]

  static confirmPasswordRules: ValidationRule[] = [
    {
      message: 'The new password confirmation is required',
      validate: (value: string) => validators.required(value),
    },
  ]
}
