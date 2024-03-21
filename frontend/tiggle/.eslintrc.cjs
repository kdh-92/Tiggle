module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  ignorePatterns: ['dist', '.eslintrc.cjs'],
  parser: '@typescript-eslint/parser',
  extends: [
    "plugin:@typescript-eslint/eslint-recommended",
    "plugin:@typescript-eslint/recommended",
    "plugin:prettier/recommended",
    "plugin:import/recommended",
    "plugin:import/typescript",
    "prettier"
  ],
  rules: {
    "prettier/prettier": "error",
    "@typescript-eslint/no-explicit-any": "off",
    "@typescript-eslint/explicit-function-return-type": "off",
    "@typescript-eslint/no-var-requires": "off",
    "import/order": [
      "error",
      {
        "newlines-between": "always",
        alphabetize: {
          order: "asc",
          caseInsensitive: false,
        },
        groups: [
          "builtin",
          "external",
          "internal",
          ["sibling", "index", "parent"],
          "type",
        ],
        pathGroups: [
          {
            pattern: "{react*,react*/**}",
            group: "external",
            position: "before",
          },
          {
            pattern: "{@/*,@/*/**}",
            group: "internal",
            position: "after",
          },
        ],
        pathGroupsExcludedImportTypes: [],
      },
    ],
    "import/newline-after-import": ["error", { count: 1 }],
    "import/no-unresolved": "off",
    "import/named": "off",
    "import/namespace": "off",
    "import/default": "off",
    "import/no-named-as-default": "off",
    "import/prefer-default-export": "off",
    "import/no-mutable-exports": "off",
    "import/no-named-as-default-member": "off",
  },
  settings: {
    "import/parser": {
      "@typescript-eslint/parser": [".ts", ".tsx"],
    },
    "import/resolver": {
      typescript: true,
      node: true,
    },
  },
};
