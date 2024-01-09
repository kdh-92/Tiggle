/** @type {import('jest').Config} */
const config = {
  preset: "ts-jest",
  testEnvironment: "jest-environment-jsdom",
  setupFilesAfterEnv: ["<rootDir>/test/setupTest.js"],
  transform: {
    "^.+\\.(ts|js|tsx|jsx)$": [
      "ts-jest",
      {
        tsconfig: "<rootDir>/tsconfig.json", // Adjust the path accordingly
        isolatedModules: true, // Enable isolatedModules to handle ES modules
      },
    ],
    "^(?!.*\\.(js|jsx|mjs|cjs|ts|tsx|css|json)$)":
      "<rootDir>/test/config/fileTransform.js",
  },
  transformIgnorePatterns: [
    "/node_modules/(?!antd)/", // Ignore node_modules except for Ant Design
  ],
  moduleNameMapper: {
    "@/(.*)": "<rootDir>/src/$1",
  },
};

module.exports = config;
