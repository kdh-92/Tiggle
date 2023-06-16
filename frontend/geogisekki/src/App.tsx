
const App = () => {

    return (
        <div>
            <h1>{process.env.NODE_ENV}</h1>
            <h1>{process.env.REACT_APP_API_URL}</h1>
        </div>
    )
}

export default App;
