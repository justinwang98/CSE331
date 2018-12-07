import React, { Component } from 'react';
import './App.css';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import * as fetch from "node-fetch";

class App extends Component {

    state = {
        srcBuilding: '',
        destBuilding: '',
        ctx: '',
        oldCoorSrc: '',
        oldCoorDest: ''
    };

    componentDidMount() {
        const canvas = this.refs.canvas;
        this.setState({ctx : canvas.getContext("2d")});
        const image = this.refs.img;
        image.onload = () => {
            this.state.ctx.scale(.2,.2);
            this.state.ctx.drawImage(image, 0, 0);
        }
    }

    handleClickRes() {
        this.state.ctx.clearRect(0,0, 4330, 2964);
        this.state.ctx.drawImage(this.refs.img, 0, 0);
        this.setState({srcBuilding: ''});
        this.setState({destBuilding: ''});
        this.setState({oldCoorSrc : ''});
        this.setState({oldCoorDest : ''});
    }


    handleSRCChange = event => {
        this.setState({srcBuilding: event.target.value});
        this.state.ctx.clearRect(0,0, 4330, 2964);
        this.state.ctx.drawImage(this.refs.img, 0, 0);
        if (this.state.oldCoorDest !== '') {
            this.drawCircle(this.state.oldCoorDest);
        }
        fetch('http://localhost:8080/building?name=' + event.target.value, {
            method: 'get',
            headers: {'Content-Type': 'application/json'},
        })
            .then(response => {return response.json()})
            .then(json => {this.drawCircle(json);
                this.setState({oldCoorSrc: json});
            })
    };

    handleDESTChange = event => {
        this.setState({destBuilding: event.target.value});
        this.state.ctx.clearRect(0,0, 4330, 2964);
        this.state.ctx.drawImage(this.refs.img, 0, 0);
        if (this.state.oldCoorSrc !== '') {
            this.drawCircle(this.state.oldCoorSrc);
        }
        fetch('http://localhost:8080/building?name=' + event.target.value, {
            method: 'get',
            headers: {'Content-Type': 'application/json'},
        })
            .then(response => {return response.json()})
            .then(json => {this.drawCircle(json);
                this.setState({oldCoorDest: json});
            })
    };

    drawCircle(coor) {
        let coords = coor[0];
        let cArr = coords.split(",");
        let X = parseFloat(cArr[0]);
        let Y = parseFloat(cArr[1]);
        this.state.ctx.beginPath();
        this.state.ctx.arc(X,Y, 30, 0, 2 * Math.PI);
        this.state.ctx.fillStyle = 'red';
        this.state.ctx.fill();
        this.state.ctx.closePath();
    }

    drawLine(srcX, srcY, destX, destY) {
        let sX = parseFloat(srcX);
        let sY = parseFloat(srcY);
        let dX = parseFloat(destX);
        let dY = parseFloat(destY);
        this.state.ctx.beginPath();
        this.state.ctx.moveTo(sX, sY);
        this.state.ctx.lineTo(dX, dY);
        this.state.ctx.strokeStyle = 'red';
        this.state.ctx.lineWidth = 10;
        this.state.ctx.stroke();
        this.state.ctx.closePath();
    }

handleClickExe = () => {
    if (this.state.srcBuilding !== '' && this.state.destBuilding !== '') {
        fetch('http://localhost:8080/paths?src=' + this.state.srcBuilding + '&dest=' + this.state.destBuilding, {
            method: 'get',
            headers: {'Content-Type': 'application/json'},
        })
            .then(response => {return response.json()})
            .then(json => {
                for (let i = 0; i < json.length; i++) {
                    let coords = json[i];
                    let splited = coords.split(":");
                    let src = splited[0].split(",");
                    let dest = splited[1].split(",");
                    if (i === 0) {
                        this.drawCircle(src[0], src[1]);
                    }
                    if (i === json.length - 1) {
                        this.drawCircle(dest[0], dest[1]);
                    }
                    this.drawLine(src[0], src[1], dest[0], dest[1]);
                }
            })
    } else {
        alert("Please select both a Source and Destination building");
    }
}



    render() {

        return (
            <div className="App">
                <header className="App-header">
                    <InputLabel>Starting Building</InputLabel><form autoComplete="off">
                    <FormControl>
                        <Select
                            open={this.state.open}
                            onClose={this.handleClose}
                            onOpen={this.handleOpen}
                            value={this.state.srcBuilding}
                            onChange={this.handleSRCChange}
                        >
                            <MenuItem value={"BAG"}>Bagley Hall (East Entrance)</MenuItem>
                            <MenuItem value={"BAG (NE)"}>Bagley Hall (Northeast Entrance)</MenuItem>
                            <MenuItem value={"BGR"}>By George</MenuItem>
                            <MenuItem value={"CHL"}>Chemistry Library (West Entrance)</MenuItem>
                            <MenuItem value={"CHL (NE)"}>Chemistry Library (Northeast Entrance)</MenuItem>
                            <MenuItem value={"CHL (SE)"}>Chemistry Library (Southeast Entrance)</MenuItem>
                            <MenuItem value={"CMU"}>Communications Building</MenuItem>
                            <MenuItem value={"CSE"}>Paul G. Allen Center for Computer Science & Engineering</MenuItem>
                            <MenuItem value={"DEN"}>Denny Hall</MenuItem>
                            <MenuItem value={"EEB"}>Electrical Engineering Building (North Entrance)</MenuItem>
                            <MenuItem value={"EEB (S)"}>Electrical Engineering Building (South Entrance)</MenuItem>
                            <MenuItem value={"FSH"}>Fishery Sciences Building</MenuItem>
                            <MenuItem value={"GWN"}>Gowen Hall</MenuItem>
                            <MenuItem value={"HUB"}>Student Union Building (Main Entrance)</MenuItem>
                            <MenuItem value={"HUB (Food, S)"}>Student Union Building (South Food Entrance)</MenuItem>
                            <MenuItem value={"HUB (Food, W)"}>Student Union Building (West Food Entrance)</MenuItem>
                            <MenuItem value={"IMA"}>Intramural Activities Building</MenuItem>
                            <MenuItem value={"KNE"}>Kane Hall (North Entrance)</MenuItem>
                            <MenuItem value={"KNE (E)"}>Kane Hall (East Entrance)</MenuItem>
                            <MenuItem value={"KNE (S)"}>Kane Hall (South Entrance)</MenuItem>
                            <MenuItem value={"KNE (SE)"}>Kane Hall (Southeast Entrance)</MenuItem>
                            <MenuItem value={"KNE (SW)"}>Kane Hall (Southwest Entrance)</MenuItem>
                            <MenuItem value={"LOW"}>Loew Hall</MenuItem>
                            <MenuItem value={"MCC"}>McCarty Hall (Main Entrance)</MenuItem>
                            <MenuItem value={"MCC (S)"}>McCarty Hall (South Entrance)</MenuItem>
                            <MenuItem value={"MCM"}>McMahon Hall (Northwest Entrance)</MenuItem>
                            <MenuItem value={"MCM (SW)"}>McMahon Hall (Southwest Entrance)</MenuItem>
                            <MenuItem value={"MGH"}>Mary Gates Hall (North Entrance)</MenuItem>
                            <MenuItem value={"MGH (E)"}>Mary Gates Hall (East Entrance)</MenuItem>
                            <MenuItem value={"MGH (S)"}>Mary Gates Hall (South Entrance)</MenuItem>
                            <MenuItem value={"MGH (SW)"}>Mary Gates Hall (Southwest Entrance)</MenuItem>
                            <MenuItem value={"MLR"}>Miller Hall</MenuItem>
                            <MenuItem value={"MNY"}>Meany Hall (Northeast Entrance)</MenuItem>
                            <MenuItem value={"MNY (NW)"}>Meany Hall (Northwest Entrance)</MenuItem>
                            <MenuItem value={"MOR"}>Moore Hall</MenuItem>
                            <MenuItem value={"MUS"}>Music Building (Northwest Entrance)</MenuItem>
                            <MenuItem value={"MUS (E)"}>Music Building (East Entrance)</MenuItem>
                            <MenuItem value={"MUS (S)"}>Music Building (South Entrance)</MenuItem>
                            <MenuItem value={"MUS (SW)"}>Music Building (Southwest Entrance)</MenuItem>
                            <MenuItem value={"OUG"}>Odegaard Undergraduate Library</MenuItem>
                            <MenuItem value={"PAA"}>Physics/Astronomy Building A</MenuItem>
                            <MenuItem value={"PAB"}>Physics/Astronomy Building</MenuItem>
                            <MenuItem value={"PAR"}>Parrington Hall</MenuItem>
                            <MenuItem value={"RAI"}>Raitt Hall (West Entrance)</MenuItem>
                            <MenuItem value={"RAI (E)"}>Raitt Hall (East Entrance)</MenuItem>
                            <MenuItem value={"ROB"}>Roberts Hall</MenuItem>
                            <MenuItem value={"SAV"}>Savery Hall</MenuItem>
                            <MenuItem value={"SUZ"}>Suzzallo Library</MenuItem>
                            <MenuItem value={"T65"}>Thai 65</MenuItem>
                            <MenuItem value={"UBS"}>University Bookstore</MenuItem>
                            <MenuItem value={"UBS (Secret)"}>University Bookstore (Secret Entrance)</MenuItem>
                        </Select>
                    </FormControl>
                </form>

                    <InputLabel>Destination Building</InputLabel><form autoComplete="off">
                    <FormControl>
                        <Select
                            open={this.state.open}
                            onClose={this.handleClose}
                            onOpen={this.handleOpen}
                            value={this.state.destBuilding}
                            onChange={this.handleDESTChange}
                        >
                            <MenuItem value={"BAG"}>Bagley Hall (East Entrance)</MenuItem>
                            <MenuItem value={"BAG (NE)"}>Bagley Hall (Northeast Entrance)</MenuItem>
                            <MenuItem value={"BGR"}>By George</MenuItem>
                            <MenuItem value={"CHL"}>Chemistry Library (West Entrance)</MenuItem>
                            <MenuItem value={"CHL (NE)"}>Chemistry Library (Northeast Entrance)</MenuItem>
                            <MenuItem value={"CHL (SE)"}>Chemistry Library (Southeast Entrance)</MenuItem>
                            <MenuItem value={"CMU"}>Communications Building</MenuItem>
                            <MenuItem value={"CSE"}>Paul G. Allen Center for Computer Science & Engineering</MenuItem>
                            <MenuItem value={"DEN"}>Denny Hall</MenuItem>
                            <MenuItem value={"EEB"}>Electrical Engineering Building (North Entrance)</MenuItem>
                            <MenuItem value={"EEB (S)"}>Electrical Engineering Building (South Entrance)</MenuItem>
                            <MenuItem value={"FSH"}>Fishery Sciences Building</MenuItem>
                            <MenuItem value={"GWN"}>Gowen Hall</MenuItem>
                            <MenuItem value={"HUB"}>Student Union Building (Main Entrance)</MenuItem>
                            <MenuItem value={"HUB (Food, S)"}>Student Union Building (South Food Entrance)</MenuItem>
                            <MenuItem value={"HUB (Food, W)"}>Student Union Building (West Food Entrance)</MenuItem>
                            <MenuItem value={"IMA"}>Intramural Activities Building</MenuItem>
                            <MenuItem value={"KNE"}>Kane Hall (North Entrance)</MenuItem>
                            <MenuItem value={"KNE (E)"}>Kane Hall (East Entrance)</MenuItem>
                            <MenuItem value={"KNE (S)"}>Kane Hall (South Entrance)</MenuItem>
                            <MenuItem value={"KNE (SE)"}>Kane Hall (Southeast Entrance)</MenuItem>
                            <MenuItem value={"KNE (SW)"}>Kane Hall (Southwest Entrance)</MenuItem>
                            <MenuItem value={"LOW"}>Loew Hall</MenuItem>
                            <MenuItem value={"MCC"}>McCarty Hall (Main Entrance)</MenuItem>
                            <MenuItem value={"MCC (S)"}>McCarty Hall (South Entrance)</MenuItem>
                            <MenuItem value={"MCM"}>McMahon Hall (Northwest Entrance)</MenuItem>
                            <MenuItem value={"MCM (SW)"}>McMahon Hall (Southwest Entrance)</MenuItem>
                            <MenuItem value={"MGH"}>Mary Gates Hall (North Entrance)</MenuItem>
                            <MenuItem value={"MGH (E)"}>Mary Gates Hall (East Entrance)</MenuItem>
                            <MenuItem value={"MGH (S)"}>Mary Gates Hall (South Entrance)</MenuItem>
                            <MenuItem value={"MGH (SW)"}>Mary Gates Hall (Southwest Entrance)</MenuItem>
                            <MenuItem value={"MLR"}>Miller Hall</MenuItem>
                            <MenuItem value={"MNY"}>Meany Hall (Northeast Entrance)</MenuItem>
                            <MenuItem value={"MNY (NW)"}>Meany Hall (Northwest Entrance)</MenuItem>
                            <MenuItem value={"MOR"}>Moore Hall</MenuItem>
                            <MenuItem value={"MUS"}>Music Building (Northwest Entrance)</MenuItem>
                            <MenuItem value={"MUS (E)"}>Music Building (East Entrance)</MenuItem>
                            <MenuItem value={"MUS (S)"}>Music Building (South Entrance)</MenuItem>
                            <MenuItem value={"MUS (SW)"}>Music Building (Southwest Entrance)</MenuItem>
                            <MenuItem value={"OUG"}>Odegaard Undergraduate Library</MenuItem>
                            <MenuItem value={"PAA"}>Physics/Astronomy Building A</MenuItem>
                            <MenuItem value={"PAB"}>Physics/Astronomy Building</MenuItem>
                            <MenuItem value={"PAR"}>Parrington Hall</MenuItem>
                            <MenuItem value={"RAI"}>Raitt Hall (West Entrance)</MenuItem>
                            <MenuItem value={"RAI (E)"}>Raitt Hall (East Entrance)</MenuItem>
                            <MenuItem value={"ROB"}>Roberts Hall</MenuItem>
                            <MenuItem value={"SAV"}>Savery Hall</MenuItem>
                            <MenuItem value={"SUZ"}>Suzzallo Library</MenuItem>
                            <MenuItem value={"T65"}>Thai 65</MenuItem>
                            <MenuItem value={"UBS"}>University Bookstore</MenuItem>
                            <MenuItem value={"UBS (Secret)"}>University Bookstore (Secret Entrance)</MenuItem>
                        </Select>
                    </FormControl>
                </form>
                    <Button variant="contained" color="primary" onClick={()=> this.handleClickExe()}>
                        Execute!
                    </Button>
                    <Button variant="contained" color="primary" onClick={()=> this.handleClickRes()}>
                    Reset!
                </Button>
                    <Card>
                        <canvas ref={"canvas"} width = {866} height = {592.8}/>
                        <img ref={"img"} className="hidden" alt="img" src={require('./campus_map_copy.jpg')} height={4330/5} length={2964/5}></img>
                    </Card>
                </header>
            </div>
        );
    }
}

export default App;

