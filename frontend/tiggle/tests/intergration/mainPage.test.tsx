import MainPage from "@/pages/MainPage";
import renderWithContext from "../util/renderWithContext";

test("demo", async() => {
  renderWithContext(<MainPage />);
  expect(1).toBe(1);
});
